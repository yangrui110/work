package com.lvshou.magic.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lvshou.magic.account.entity.BuyInfo;
import com.lvshou.magic.account.service.BuyInfoService;
import com.lvshou.magic.exception.ResultEnum;

import com.lvshou.magic.result.Result;

@Controller
@RequestMapping("buyInfo")
public class BuyInfoController {

	@Autowired
	BuyInfoService buyInfoService;
	
	@ResponseBody
	@RequestMapping("findAll")
	public Result findAll() {
		return new Result<>();
	}
	
	@ResponseBody
	@PutMapping("update")
	public Result update(@RequestBody BuyInfo detail) {
		return new Result<>(ResultEnum.OK, buyInfoService.update(detail));
	}
	
	@ResponseBody
	@PostMapping("insert")
	public Result insert(@RequestBody BuyInfo detail) {
		return new Result<>(ResultEnum.OK, buyInfoService.insert(detail));
	}
	
	@ResponseBody
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable("id") String id) {
		buyInfoService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@ResponseBody
	@GetMapping("findByUserId")
	public Result findByUserId(@RequestParam("userId")String userId) {
		return new Result<>(ResultEnum.OK,buyInfoService.findByUserId(userId));
	}
}
