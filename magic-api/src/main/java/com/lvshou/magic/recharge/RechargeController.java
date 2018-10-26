package com.lvshou.magic.recharge;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.recharge.entity.Recharge;
import com.lvshou.magic.recharge.service.RechargeService;
import com.lvshou.magic.result.Result;

@RestController
@RequestMapping("recharge")
public class RechargeController {

	@Autowired
	RechargeService rechargeService;
	
	@RequestMapping("find/{account}")
	public Result findOne(@PathVariable String account) {
		Recharge recharge=new Recharge();
		return new Result<>(ResultEnum.OK, rechargeService.findOne(recharge));
	}
	@GetMapping("findByUserId/{userId}")
	public Result finds(@PathVariable String userId) {
		return new Result<>(ResultEnum.OK,rechargeService.findByUserId(userId));
	}
	@RequestMapping("findAll")
	public Result findAll() {
		return new Result<>(ResultEnum.OK, rechargeService.findAll(0, 3).getContent());
	}
	
	@RequestMapping("delete/{id}")
	public Result delete(@PathVariable String id) {
		rechargeService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@RequestMapping("insert")
	public Result insert() {
		Recharge recharge=new Recharge();
		recharge.setDescribetion("zhanghugren");
		recharge.setMoney(new BigDecimal(100));
		recharge.setOperator("self");
		return new Result<>(ResultEnum.OK,rechargeService.insert(recharge));
	}
	
	@RequestMapping("update")
	public Result update() {
		Recharge recharge=new Recharge();
		recharge.setMoney(new BigDecimal(1000));
		return new Result<>(ResultEnum.OK,rechargeService.update(recharge));
	}
}
