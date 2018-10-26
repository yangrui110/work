package com.lvshou.magic.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.sys.entity.SysSetting;
import com.lvshou.magic.sys.service.SysSettingService;

@Controller
@RequestMapping("sys")
public class SysController {

	@Autowired
	private SysSettingService service;
	
	@ResponseBody
	@GetMapping("find")
	public Result sys() {
		List lists=new ArrayList<>();
		lists.add(service.getOne());
		return new Result<>(ResultEnum.OK,lists,1);
	}
	
	@GetMapping("get")
	public ModelAndView sysindex() {
		return new ModelAndView("sys");
	}
	@PostMapping("ups")
	public ModelAndView sysUp(SysSetting setting) {
		service.update(setting);
		return new ModelAndView("sys");
	}
	
	@GetMapping("editor")
	public ModelAndView sysEditor(Map<String, Object>map) {
		map.put("sys", service.getOne());
		return new ModelAndView("sysEditor");
	}
}
