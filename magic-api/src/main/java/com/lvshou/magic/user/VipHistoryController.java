package com.lvshou.magic.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.service.VipHistoryService;

@RestController
@RequestMapping("vipHistory")
public class VipHistoryController {

	@Autowired
	private VipHistoryService vipHistoryService;
	
	@GetMapping("finds")
	public Result finds() {
		return new Result<>(ResultEnum.OK);
	}
}
