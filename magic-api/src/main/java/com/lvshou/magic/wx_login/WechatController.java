package com.lvshou.magic.wx_login;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lvshou.magic.config.PayProperties;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.login.WxLoginService;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.order.service.OrderMainService;
import com.lvshou.magic.order.vo.OrderMainVo;
import com.lvshou.magic.payservice.Payment;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.statics.Info;
import com.lvshou.magic.sys.entity.SysSetting;
import com.lvshou.magic.sys.service.SysSettingService;
import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.user.status.VipStatus;
import com.lvshou.magic.util.IpUtils;
import com.lvshou.magic.util.XMLUtil;
import com.lvshou.magic.utils.MainUUID;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
	
	/**
	 * @author yangrui
	 * */

	@Autowired
	private PayProperties payProperties;
	@Autowired
	private MoneyService moneyService;
	@Autowired
	private WxLoginService wxLoginService;
	
	@Autowired
	private Payment payment;
	
	@Autowired
	private OrderMainService orderMainService;
	
	@Autowired
	private SysSettingService setting;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private UserService userService;
	
	/*@Autowired
	private VerificationService verificationService;*/

	@ResponseBody
	@GetMapping("autoLoginIn")
	public Result autoLoginIn(@RequestParam(value="userId",required=false)String userId,
			@RequestParam(value="token",required=false)String token) {
		//判断是否登陆过，如果登陆过，就自动登录
		if(userId!=null&&token!=null) {
			String token_1=stringRedisTemplate.opsForValue().get("token_"+userId);
			if(token_1==null) {
				return new Result<>(ResultEnum.LOGIN);
			}
			if(!token.equals(token_1)) {
				throw new CommonException(ResultEnum.LOGIN_IGGAL);
			}
			User user=userService.findUser(new User().setId(userId));
			UserVo vo=UserConvert.covertToVo(user,0);
			//vo.setAccess_token(token_1);
			return new Result<>(ResultEnum.OK,vo);
		}
		return new Result<>(ResultEnum.LOGIN);
	}
	
	
    /** 
     * 网页微信授权登录接口 
     * @return 
     * @throws UnsupportedEncodingException 
     */
	
	@GetMapping("pay")
	public String payOrder(@RequestParam("userId")String userId,@RequestParam(value="price",required=true)String price,HttpServletRequest request) throws UnsupportedEncodingException {
		return "redirect:"+payment.sign(payProperties.getPay_notify(),userId,MainUUID.getUUID(), price, IpUtils.getIpAddr(request));
	}
	
    @GetMapping(value="meiyeLogin")
    public String meiyeLogin() {
    	return "redirect:"+wxLoginService.shangcheng();
    }
    @GetMapping("meiye")
    public String meiye(HttpServletRequest request,RedirectAttributes redirectAttributes) {
    	UserVo vo=wxLoginService.register(request);
    	
    	//设置redis数据
    	/*String token=stringRedisTemplate.opsForValue().get("token_"+vo.getId());
		if(StringUtils.isEmpty(token)) {
			token=MainUUID.getUUID();
		}
		stringRedisTemplate.opsForValue().set("token_"+vo.getId(), token, 7200,TimeUnit.SECONDS );*/
    	redirectAttributes.addAttribute("userId",vo.getWid());
    	redirectAttributes.addAttribute("token", "123");
    	System.out.println("美业获取到的openid为"+vo.getWid()+"");
    	if(!StringUtils.isEmpty(vo.getId())) {
    		redirectAttributes.addAttribute("userId",vo.getId());
    		return "redirect:http://www.wanxidi.com.cn/index.html";
    				//"redirect:http://www.wanxidi.com.cn/user/index.html";
    	}
    	redirectAttributes.addAttribute("userId",vo.getWid());
    	return "redirect:http://www.wanxidi.com.cn/user/update.html";
    }
    /**扫描二维码，将会跳转到shareOne界面，并且携带parent_code参数*/
    @GetMapping(value = "/share")
    public String share(@RequestParam(value="parent",required=true)String parent_code) throws Exception {
    	
		return wxLoginService.getShareLocation(parent_code);
    }
    
    @GetMapping("shareOne")
    public String shareOne(HttpServletRequest request) throws Exception {
    	
    	return "redirect:"+wxLoginService.dealShare(request);
    }
    /**授权成功后，将会跳转到modify方法，产生新的用户*/
    @GetMapping(value = "/login")
    public String wxLogin() throws Exception {
    	return "redirect:"+wxLoginService.login();
    }
	//授权成功的回调地址,返回首页，携带分配的token值
    @RequestMapping(value="modify")
    public String index(HttpServletRequest request,RedirectAttributes redirectAttributes,HttpServletResponse response) {
    	UserVo vo=wxLoginService.modify(request);
    	redirectAttributes.addAttribute("token", "123");
    	System.out.println("获取到的openid为"+vo.getWid()+"");
    	if(!StringUtils.isEmpty(vo.getId())) {
    		redirectAttributes.addAttribute("userId",vo.getId());
    		return "redirect:http://www.wanxidi.com.cn/user/index.html";
    	}
    	redirectAttributes.addAttribute("userId",vo.getWid());
    	return "redirect:http://www.wanxidi.com.cn/user/update.html";
    }
    /**个人中心的入口地址*/
    @GetMapping(value="person")
    public String wxPerson() {
    	return "redirect:"+wxLoginService.wxPerson();
    }
    @GetMapping(value="personModify")
    public String personModify(HttpServletRequest request,RedirectAttributes redirectAttributes) {
    	UserVo vo=wxLoginService.register(request);
    	redirectAttributes.addAttribute("token", "123");
    	System.out.println("获取到的openid为"+vo.getWid()+"");
    	if(!StringUtils.isEmpty(vo.getId())) {
    		redirectAttributes.addAttribute("userId",vo.getId());
    		return "redirect:http://www.wanxidi.com.cn/user/index.html";
    	}
    	redirectAttributes.addAttribute("userId",vo.getWid());
    	return "redirect:http://www.wanxidi.com.cn/user/update.html";
    }
    //订单支付成功的结果回调地址
    @ResponseBody
    @PostMapping("notify")
    public String notify1(@RequestBody String request) throws Exception {
    	System.out.println("回调成功");
    	//需要做签名验证，防止虚假信息的出现
    	boolean bl=payment.token(request);
    	if(bl) {
    		Map map=null;
    		String out_trade_no=null;
			try {
				map = XMLUtil.Dom2Map(DocumentHelper.parseText(request));
				out_trade_no=(String) map.get("out_trade_no");
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**订单设置为已支付状态*/
    		orderMainService.havePayOrder(out_trade_no);
    		OrderMainVo orderMain=orderMainService.findById(out_trade_no);
    		//userService.rewardDirect(orderMain.getUserId(),Integer.parseInt(mn));
    		//总积分满9800就会成为正式会员
    		User user=userService.findUser(new User().setId(orderMain.getUserId()));
    		//查询起始资金库
    		SysSetting sysSetting=setting.getOne();
    		if(sysSetting.getVipMoney().compareTo(orderMain.getTotalPrice())==-1&&user.getVip()<VipStatus.VIP) {
    			UserVo vo=new UserVo();
    			user.setVip(VipStatus.VIP);
    			BeanUtils.copyProperties(user, vo);
    			userService.update(vo);
    			//更新Money
    			BigDecimal left=orderMain.getTotalPrice().subtract(sysSetting.getVipMoney());
    			if(left.compareTo(new BigDecimal(0))==1) {
    				moneyService.addTotalIntegralById(left, user.getId());
    			}
    		}
    		
    		return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
    	}
    	else {
			return "";
		}
    }
    
    @ResponseBody
    @PostMapping("chargeNotify")
    public String notify2(@RequestBody String request) {
    	System.out.println("充值回调");
    	boolean bl=payment.token(request);
    	User user=new User();
    	if(bl) {
    		Map map=null;
    		String openId=null;
    		String total_fee=null;
			try {
				map = XMLUtil.Dom2Map(DocumentHelper.parseText(request));
				openId=(String) map.get("openid");
				
				user.setWid(openId);
				user=userService.findUser(user);
				total_fee=(String) map.get("total_fee");
				LoggerFactory.getLogger(WechatController.class).info("openId为{}的用户，用户的名字是{}，用户的numId是{}，充值了{}/10元的金额",openId,user.getName(),user.getNumId(),total_fee);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		moneyService.addMoneyById(new BigDecimal(total_fee).divide(new BigDecimal(100)),user.getId());
    		return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
    	}
    	else {
			return "";
		}
    }
    
    @ResponseBody
    @RequestMapping(value = "/",method=RequestMethod.GET)
    public String checkName(@RequestParam(name="signature")String signature,
                            @RequestParam(name="timestamp")String timestamp,
                            @RequestParam(name="nonce")String nonce,
                            @RequestParam(name="echostr")String echostr){
        System.out.println("-----------------------开始校验------------------------");
        //排序
        String sortString = sort(Info.token, timestamp, nonce);
        //加密
        String myString = sha1(sortString);
        //校验
        if (myString != null && myString != "" && myString.equals(signature)) {
            System.out.println("签名校验通过");
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
            return echostr;
        } else {
            System.out.println("签名校验失败");
            return "";
        }
    }
    
    private String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }
    
    private String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
