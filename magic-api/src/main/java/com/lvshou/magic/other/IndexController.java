package com.lvshou.magic.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;

@Controller
public class IndexController {

	@ResponseBody
	@GetMapping("ad_login")
	public ModelAndView login() {
		return new ModelAndView("login");
		//return new Result<>(ResultEnum.LOGIN);
	}
	
}
