package com.lvshou.magic.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.poifs.property.Parent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.convert.StringConvert;
import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.dao.UserDao;
import com.lvshou.magic.user.dao.UserHistoryDao;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserHistory;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.user.status.VipStatus;

import javassist.expr.NewArray;


@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	@Autowired
	UserHistoryDao userHistoryDao;
	@PostMapping("find")
	public Result findUser(User user,
			@RequestParam(value="page",defaultValue="0",required=false) int page,
			@RequestParam(value="size",defaultValue="10",required=false) int size) {
		return new Result<>(ResultEnum.OK, userService.findUsers(user,page,size).getContent());
	}
	@GetMapping("findById")
	public Result findOne(@RequestParam("id")String id) {
		User user=new User();
		user.setId(id);
		return new Result<>(ResultEnum.OK,userService.findUser(user));
	}
	@GetMapping("findByWid")
	public Result findByWid(@RequestParam("id")String id) {
		User user=new User();
		//user.setId(id);
		user.setWid(id);
		return new Result<>(ResultEnum.OK,userService.findUser(user));
	}
	@GetMapping("findAll")
	public Result findAll(@RequestParam(value="page",defaultValue="0",required=false) int page,@RequestParam(value="size",defaultValue="10",required=false) int size) {
		Page page2=userService.findAll(page, size);
		
		return new Result<>(ResultEnum.OK,page2.getContent());
	}
	@PostMapping("insert")
	public Result insert(UserVo user) throws Exception {
		if(user==null) {
			return new Result<>(ResultEnum.ERROR);
		}
		User user2=new User();
		BeanUtils.copyProperties(user, user2);
		userService.insert(user2);
		return new Result<>(ResultEnum.OK,user2);
	}
	@RequestMapping("delete/{id}")
	public Result delete(@PathVariable String id) {
		userService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	@PutMapping("update")
	public Result update(@RequestBody UserVo user) throws Exception {
		if(user.getId()==null) {
			return new Result<>(ResultEnum.ID_NOT_EXIT);
		}
		//User user2=new User();
		//BeanUtils.copyProperties(user, user2);
		//userService.update(user2);
		return new Result<>(ResultEnum.OK, userService.update(user));
	}
	
	@GetMapping("findVip")
	public Result findVip(@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="rows",defaultValue="10",required=false)int size) {
		User user=new User();
		user.setVip(2);
		return new Result<>(ResultEnum.OK,userService.findUsers(user, page-1, size).getContent());
	}
	@ResponseBody
	@GetMapping("findAllChild/{id}")
	public Result findAllChild(@PathVariable String id) throws Exception {
		
		User user=new User();
		user.setId(id);
		user =userService.findUser(user);
		List<UserVo> vos=null;
		if(user!=null) {
			vos=new ArrayList<UserVo>();
			userService.allChilds(user, 3, vos);
		}
		return new Result<>(ResultEnum.OK,vos);
	}
	
	/*@ResponseBody
	@GetMapping("findDied")
	public Result findDieds() {
		return new Result<>(ResultEnum.OK,userService.findDied());
	}
	
	@ResponseBody
	@GetMapping("findPartnerAndDirect")
	public Result findPart() {
		List<User> lists=userService.findPartnerAndDirect();
		return new Result<>(ResultEnum.OK,lists,lists.size());
	}*/
	@PutMapping("bindOldVip")
	@ResponseBody
	public Result bindOldVip(@RequestBody UserVo vo) {
		System.out.println(vo.getName()+"---"+vo.getPhone()+"---"+vo.getOwnReferCode());
		if(StringUtils.isEmpty(vo.getOwnReferCode()))
			return new Result<>(ResultEnum.OK,null);
		vo.setOwnReferCode(vo.getOwnReferCode().toUpperCase());
		User user=userService.bindOldVip(vo);
		if(user==null) return new Result<>(ResultEnum.OK,null);
		else {
			
			return new Result<>(ResultEnum.OK,user);
		}
	}
	
	@ResponseBody
	@GetMapping("other")
	public Result other() throws Exception {
		List<User> users=userService.findAllUser();
		//转换为List<String>
		List<String> results=StringConvert.convertListUser(users);
		long start=System.currentTimeMillis();
		int lastAmount=0;
		String numId="";
		for (String string : results) {
			List<String> rm=new ArrayList<>();
			List<UserVo> vos=new ArrayList<>();
			StringConvert.allChilds(string, results, rm, 3);
			for (String string2 : rm) {
				UserVo vo=(UserVo)StringConvert.toUserObject(string2, UserVo.class);
				vos.add(vo);
			}
			if(vos.size()>lastAmount) {
				lastAmount=vos.size();
				numId=string;
			}
		}
		System.out.println("最大的下级数目是："+lastAmount+"该用户的信息:"+numId);
		long end=System.currentTimeMillis();
		return new Result<>(ResultEnum.OK,new ModelMap("运行耗时(单位/s)",(end-start)/1000));
	}
	@ResponseBody
	@GetMapping("one/{id}")
	public Result one(@PathVariable("id") String id) throws Exception {
		User userm=userService.findUser(new User().setId(id));
		//转换为List<String>
		String string=StringConvert.parseUser(userm);
		List<User> users=userService.findAllUser();
		//转换为List<String>
		List<String> results=StringConvert.convertListUser(users);
		
		long start=System.currentTimeMillis();
		List<String> rm=new ArrayList<>();
		StringConvert.allChilds(string, results, rm, 3);
		List<UserVo> rtm=new ArrayList<>();
		for (String string2 : rm) {
			rtm.add((UserVo)StringConvert.toUserObject(string2, UserVo.class));
		}
		System.out.println(rtm.size());
		long end=System.currentTimeMillis();
		return new Result<>(ResultEnum.OK,new ModelMap("运行耗时(单位/ms)",end-start));
	}
}
