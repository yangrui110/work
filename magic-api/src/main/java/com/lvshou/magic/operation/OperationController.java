package com.lvshou.magic.operation;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.operation.entity.Operation;
import com.lvshou.magic.operation.service.OperationService;
import com.lvshou.magic.result.Result;

@RestController
@RequestMapping("operation")
public class OperationController {

	@Autowired
	OperationService operationService;
	
	@RequestMapping("find/{id}")
	public Result findOne(@PathVariable String id) {
		
		Operation operation=new Operation();
		operation.setId(id);
		return new Result<>(ResultEnum.OK, operationService.findOne(operation));
	}
	
	
	@RequestMapping("findAll")
	public Result findAll(@RequestParam(value="userId",required=true)String userId) {
		return new Result<>(ResultEnum.OK, operationService.findAll(userId));
	}
	
	@RequestMapping("update/{id}")
	public Result update(@PathVariable String id) {
		Operation operation=new Operation();
		operation.setId(id);
		operation.setMoney(new BigDecimal(1000));
		return new Result<>(ResultEnum.OK, operationService.update(operation));
	}
	
	@RequestMapping("insert/{money}")
	public Result insert(@PathVariable long money) {
		Operation operation=new Operation();
		operation.setDescribetion("describe");
		operation.setOperate("consume");
		operation.setMoney(new BigDecimal(money));
		return new Result<>(ResultEnum.OK, operationService.insert(operation));
	}
	
	@RequestMapping("delete/{id}")
	public Result delete(@PathVariable String id) {
		operationService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
}
