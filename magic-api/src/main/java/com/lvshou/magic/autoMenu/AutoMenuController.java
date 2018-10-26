package com.lvshou.magic.autoMenu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.entity.Advertise;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.menu.MenuCreateInterface;
import com.lvshou.magic.message.MessageService;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.service.AdvertiseService;
import com.lvshou.magic.statics.Info;

@RestController
@RequestMapping("autoMenu")
public class AutoMenuController {

	@Autowired
	private MenuCreateInterface menuCreateInterface;
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private AdvertiseService advertiseService;
	@ResponseBody
	@GetMapping("create")
	public Result menu() {
		return new Result<>(ResultEnum.OK,menuCreateInterface.create());
	}
	@ResponseBody
	@GetMapping("click")
	public Result click(HttpServletRequest request) {
		//DocumentHelper.parseText(text);
		//System.out.println(request.getParameter(""));
		return new Result<>(ResultEnum.OK);
	}
	@ResponseBody
	@PostMapping("/")
	public String click1(HttpServletRequest request) throws Exception  {
		Advertise advertise=advertiseService.findByType(2, 0, 10).get(0);
		String background=advertise.getPics().substring(advertise.getPics().lastIndexOf("/")+1);
		return messageService.response(request,background);
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
