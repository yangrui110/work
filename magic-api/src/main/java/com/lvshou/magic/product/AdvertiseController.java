package com.lvshou.magic.product;

import java.util.Map;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.entity.Advertise;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.service.AdvertiseService;

@Controller
@RequestMapping("advertise")
public class AdvertiseController {

	@Autowired
	private AdvertiseService advertiseService;
	
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="size",defaultValue="100000",required=false)int size) {
		return new Result<>(ResultEnum.OK,advertiseService.findAll(page-1, size));
	}
	@ResponseBody
	@GetMapping("findByType")
	public Result findByType(@RequestParam(value="type",defaultValue="0",required=false)int type,
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="rows",defaultValue="10000",required=false)int size){
		return new Result<>(ResultEnum.OK,advertiseService.findByType(type,page-1,size));
	}
}
