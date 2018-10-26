package com.lvshou.magic.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.WriterException;
import com.lvshou.magic.base.vo.PagedVo;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.operation.entity.Operation;
import com.lvshou.magic.operation.service.OperationService;
import com.lvshou.magic.operation.statics.Constant;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.convert.StringConvert;
import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.dao.UserDao;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.export.ExportUser;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.user.status.VipStatus;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.utils.QrCodeUtils;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	ExportUser exportUser;
	@Autowired
	OperationService operationService;
	@Autowired
	UserDao userDao;
	@ResponseBody
	@PostMapping("find")
	public Result findUser(User user) {
		
		return new Result<>(ResultEnum.OK, userService.findUser(user));
	}
	@ResponseBody
	@GetMapping("delBind")
	public Result delBind(@RequestParam("id")String id) {
		userService.delBind(id);
		return new Result<>(ResultEnum.OK,new ModelMap("result",true));
	}
	@ResponseBody
	@GetMapping("findById")
	public Result findOne(@RequestParam("id")String id) {
		return new Result<>(ResultEnum.OK,userService.findUser(new User().setId(id)));
	}
	@ResponseBody
	@GetMapping("findCoupons")
	public List findCoupons(@RequestParam("id")String id) {
		return userService.getCoupons(id);
	}
	@ResponseBody
	@GetMapping("findUsers")
	public Result findByCode(@RequestParam(value="referral_code",required=false)String referral_code,
			@RequestParam(value="numId",required=false)String numId,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="parentCode",required=false)String parentCode,
			@RequestParam(value="directCode",required=false)String directCode,
			@RequestParam(value="type",required=false,defaultValue="0")int type,
			@RequestParam(value="page",required=false,defaultValue="1")int page,
			@RequestParam(value="limit",required=false,defaultValue="10") int rows,
			@RequestParam(value="vip",required=false,defaultValue="0") int vip,
			@RequestParam(value="bindWechat",required=false,defaultValue="0") int bindWechat) throws Exception {
		if(!StringUtils.isEmpty(directCode)) {
			List<User> users=userService.findByDirectCode(directCode);
			return new Result<>(ResultEnum.OK,users,users.size());
		}
		if(!StringUtils.isEmpty(parentCode)) {
			User user=new User();
			user.setReferralCode(parentCode);
			List<User> lists=userService.typeChilds(user, type);
			List<User> result=new ArrayList<>();
			int size=page*rows>lists.size()?lists.size():page*rows;
			for (int i = (page-1)*rows; i < size; i++) {
				result.add(lists.get(i));
			}
			return new Result<>(ResultEnum.OK,result,lists.size());
		}
		/**查询没有绑定微信的数据*/
		if(bindWechat!=0) return new Result<>(ResultEnum.OK,userService.findNoIfy(page, rows),userService.amountNoIfy());
		if(vip!=0)
			return new Result<>(ResultEnum.OK,userService.findPageAllVips(vip, page, rows),userService.findByVip(vip));
		/**没有传值的话查询所有数据*/
		if(StringUtils.isEmpty(referral_code)&&StringUtils.isEmpty(numId)&&StringUtils.isEmpty(name)&&StringUtils.isEmpty(phone)) {
			return new Result<>(ResultEnum.OK,userService.findAll(page-1, rows).getContent(),userService.allUsers());
		}
		User user=new User();
		user.setReferralCode(referral_code).setNumId(numId).setName(name).setPhone(phone);
		List<User>list=userService.findUsers(user, page-1, rows).getContent();
		if(!StringUtils.isEmpty(name)) {
			return new Result<>(ResultEnum.OK,list,userService.countName(name));
		}
		return new Result<>(ResultEnum.OK,list,list.size());
	}
	@ResponseBody
	@GetMapping("findByPhone")
	public PagedVo findByPhone(@RequestParam String phone,
			@RequestParam(value="page",required=false,defaultValue="1") int page,
			@RequestParam(value="limit",required=false,defaultValue="10") int rows) {
		return new PagedVo(userService.allUsers(),userService.findByPhone(phone, page-1, rows).getContent());
	}
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(
			@RequestParam(value="page",required=false,defaultValue="1") int page,
			@RequestParam(value="limit",required=false,defaultValue="10") int rows) {
		Page<User> page2=userService.findAll(page-1, rows);
		
		return new Result<>(ResultEnum.OK,page2.getContent(),userService.allUsers());
	}
	///下面是web的控制
	
	@GetMapping("userList")
	public String userList() {
		return "userList";
	}
	
	@GetMapping("editor/{id}")
	public ModelAndView editorUser(@PathVariable("id") String id,Map<String, Object> map) {
		User user=new User();
		user.setId(id);
		User user2=userService.findUser(user);
		map.put("user", user2);
		return new ModelAndView("editor",map);
	}
	@GetMapping("add")
	public ModelAndView addUser(Map<String, Object> map) {
		map.put("user", new User());
		return new ModelAndView("editor",map);
	}
	@PostMapping("save")
	public ModelAndView update(User user,Map<String, Object> map, BindingResult bindingResult) throws Exception {
		if(user.getId()==null||user.getId().equals("")) {
			userService.insert(user);
		}else {
			UserVo vo=new UserVo();
			BeanUtils.copyProperties(user,vo);
			userService.update(vo);
		}
		return new ModelAndView("userList",map);
	}
	
	@DeleteMapping("delete/{id}")
	public ModelAndView delete(@PathVariable String id,Map<String, Object> map) {
		if(StringUtils.isEmpty(id)) {
			map.put("msg", "出错了~~~,错误原因是传来的用户id为空");
			return new ModelAndView("error",map);
		}
		userService.delete(id);
		map.put("msg", "删除成功");
		return new ModelAndView("userList",map);
	}
	
	@ResponseBody
	@PostMapping("findUsers")
	public PagedVo findUsers(User user,
			@RequestParam(value="page",required=false,defaultValue="1")Integer page,
			@RequestParam(value="limit",required=false,defaultValue="10")Integer size) {
		return new PagedVo(userService.allUsers(),userService.findAll(page-1, size).getContent());
	}
	
	@ResponseBody
	@GetMapping("findVip")
	public Result findVip(@RequestParam(value="referral_code",required=false)String referral_code,
			@RequestParam(value="numId",required=false)String numId,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="partner",required=false)String partner,
			@RequestParam(value="director",required=false)String director,
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="limit",defaultValue="10",required=false)int size) {

		User user=new User();
		user.setVip(2);
		if(StringUtils.isEmpty(referral_code)&&StringUtils.isEmpty(numId)&&StringUtils.isEmpty(name)&&StringUtils.isEmpty(phone)) {
			return new Result<>(ResultEnum.OK,userService.findUsers(user, page-1, size).getContent(),userService.findByVip(user.getVip()));
		}
		user.setReferralCode(referral_code).setNumId(numId).setName(name).setPhone(phone);
		List<User> lists=userService.findUsers(user, page-1, size).getContent();
		if(!StringUtils.isEmpty(name)) {
			return new Result<>(ResultEnum.OK,userService.findUsers(user, page-1, size).getContent(),userService.countVipName(name));
		}else {
			
			return new Result<>(ResultEnum.OK,lists,lists.size());
		}
	}
	
	
	@GetMapping("vipLists")
	public ModelAndView vipLists() {
		//throw new CommonException("自定义错误界面");
		return new ModelAndView("vipList");
	}
	@ResponseBody
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<User> users) {
		Iterator<User> iterator=users.iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			userService.delete(user.getId());
		}
		return new Result<>(ResultEnum.OK);
	}
	
	@ResponseBody
	@GetMapping("addOne")
	public Result addOne() throws Exception {
		User user=new User();
		user.setName("阿西吧");
		user.setPhone("");
		userService.insert(user);
		return new Result<>(ResultEnum.OK);
	}
	
	@GetMapping("export")
	public void export(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=false,name="bindWechat",defaultValue="0") int ify,
			@RequestParam(required=false,name="vip",defaultValue="0") int vip) throws Exception {
		exportUser.export(request, response,vip,ify);
	}
	
	@ResponseBody
	@GetMapping("iter")
	public Result iter() throws Exception {
		List<User> users=userService.findAllUser();
		int allAmount=users.size();
		int num=0;
		for (User user : users) {
			user.setVip(VipStatus.VIP);
			userService.update(UserConvert.covertToVo(user, 0));
			
			System.out.println("当前更新进度:"+(num*100)/allAmount+"%");
			num++;
		}
		return new Result<>(ResultEnum.OK);
	}
	/**死会员判断
	 * @throws Exception */
	@ResponseBody
	@GetMapping("died")
	public Result died() throws Exception {
		//1.查找到所有的会员
		List<User> users=userService.findAllUser();
		//2.转为List<String>形式
		List<String> userStrings=StringConvert.convertListUser(users);
		//3.遍历每一个用户，找到每个用户下级成为vip的最大时间，然后更新user的refer_time
		int count=0;
		List<String> dieds=new ArrayList<>();
		for (String string : userStrings) {
			List<String> str=new ArrayList<>();
			
			StringConvert.allChilds(string, userStrings, str, 3);
			if(string.contains("胡梅"))
				System.out.println(1);
			int num=0;
			long maxTime=0;
			//给maxTime赋初始值
			String[] resm=string.split(";");
			for (String string3 : resm) {
				if(string3.contains("vipTime")) {
					String date=string3.split("~")[1];
					if(!date.equalsIgnoreCase("null")) {
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date2=dateFormat.parse(date);
					maxTime=date2.getTime();
					}else
						maxTime=new Date().getTime();
				}
			}
			for (String string2 : str) {
				System.out.println("当前正在遍历用户:"+count+"的第"+num+"个子节点");
				String[] res=string2.split(";");
				
				for (String string3 : res) {
					if(string3.contains("vipTime")) {
						String date=string3.split("~")[1];
						SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date2=dateFormat.parse(date);
						if(date2.getTime()>maxTime) {
							maxTime=date2.getTime();
						}
					}
				}
				num++;
			}
			//4.判断下级的最大推荐时间是否是大于90天，如果大于90，就设置为死会员
			long distance=new Date().getTime()-maxTime;
			int days=(int) (distance/(1000*60*60*24));
			if(days>90) {
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String ssm= dateFormat.format(new Date(maxTime));
				//遍历设置referTime
				int start=string.indexOf("referTime");
				string=new StringBuilder(string).replace(start+10, start+14, ssm).toString();
				dieds.add(string);
			}
			count++;
		}
		//已经遍历完毕，得到了死会员

		for(String c:dieds) {
			User user=(User) StringConvert.toUserObject(c, User.class);
			user.setVip(VipStatus.DIED);
			userDao.save(user);
		}
		return new Result<>(ResultEnum.OK,new ModelMap("result","OK"));
	}
	
	//重新生成qrcode推荐码
	@GetMapping("qrcodeNew")
	@ResponseBody
	public Result createQrcode() throws WriterException, IOException {
		
		List<User> users=userService.findAllUser();
		int count=0;
		int allAmount=users.size();
		for (User user : users) {
			String name=MainUUID.getUUID();
			QrCodeUtils.createQrCode("D:\\\\FileUpload\\\\"+name+".jpg","http://api.wanxidi.com.cn/wechat/share?parent="+user.getReferralCode());
			user.setQrcode(name+".jpg");
			userDao.save(user);
			System.out.println("当前进度:"+(count*100)/allAmount+"%");
			count++;
		}
		
		return new Result<>(ResultEnum.OK);
	}
}
