package com.lvshou.magic.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.order.service.OrderMainService;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.service.UserService;

@Controller
public class IndexController {

	@Autowired
	UserService userService;
	
	@Autowired
	OrderMainService orderMainService;
	
	@RequestMapping("main")
	public ModelAndView welcome(Map<String,Integer> map) {
		map.put("allUsers", userService.allUsers());
		map.put("allOrders", orderMainService.allOrders(0));
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date=dateFormat.parse(dateFormat.format(new Date()));
			map.put("addUsers", userService.findAdd(date));
			map.put("addOrderMain", orderMainService.findAdd(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("main",map);
	}
	
	@GetMapping("ad_login")
	public ModelAndView login() {
		return new ModelAndView("login");
		//return new Result<>(ResultEnum.OK);
	}
}
