package com.lvshou.magic.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.sys.service.SysSettingService;

@RestController
@RequestMapping("sys")
public class SysController {

	@Autowired
	private SysSettingService service;
	
	@GetMapping("find")
	public Result find() {
		return new Result<>(ResultEnum.OK,service.getOne());
	}
}
