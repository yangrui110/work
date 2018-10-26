package com.lvshou.magic.money;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.config.PayProperties;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.dao.RewardDao;
import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.payservice.Payment;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.util.IpUtils;
import com.lvshou.magic.utils.MainUUID;

@RestController
@RequestMapping("money")
public class MoneyController {

	@Autowired
	MoneyService moneyService;
	@Autowired
	PayProperties payProperties;

	@Autowired
	Payment payment;
	@RequestMapping("find/{id}")
	public Result find(@PathVariable String id) {
		Money money=new Money();
		money.setId(id);
		return new Result<>(ResultEnum.OK, moneyService.findOne(money));
	}
	
	@RequestMapping("findByUserId")
	public Result findByUserId(@RequestParam("userId")String userId) {
		Money money =new Money();
		money.setUserId(userId);
		return new Result<>(ResultEnum.OK,moneyService.findOne(money));
	}
	@RequestMapping("findAll")
	public Result findAll() {
		return new Result<>(ResultEnum.OK, moneyService.findAll(0, 3));
	};
	
	@RequestMapping("update/{id}")
	public Result update(@PathVariable String id) {
		Money money=new Money();
		money.setId(id);
		money.setBalance(new BigDecimal(1000));
		return new Result<>(ResultEnum.OK, moneyService.update(money));
	}
	
	@RequestMapping("delete/{id}")
	public Result delete(@PathVariable String id) {
		moneyService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@RequestMapping("insert/{userId}")
	public Result insert(@PathVariable String userId) {
		Money money=new Money();
		money.setBalance(new BigDecimal(100));
		money.setIntegral(new BigDecimal(100));
		money.setReward(new BigDecimal(100));
		money.setTotalIntegral(new BigDecimal(100));
		money.setTotalReward(new BigDecimal(100));
		money.setUserId(userId);
		moneyService.insert(money);
		return new Result<>(ResultEnum.OK, money);
	}
	
	@ResponseBody
	@GetMapping("chargeMoney")
	public Result chargeMoney(@RequestParam("money")String money,
			@RequestParam("userId")String userId,HttpServletRequest request) throws UnsupportedEncodingException {
		return new Result<>(ResultEnum.OK,payment.sign(payProperties.getCharge_notify(), userId, MainUUID.getUUID(), money, IpUtils.getIpAddr(request)));
	}
	@ResponseBody
	@GetMapping("startDraw")
	public Result startDraw(@RequestParam(value="userId",required=true)String userId,@RequestParam(value="money",required=true)String money) {
		return new Result<>(ResultEnum.OK,moneyService.startDraw(userId, new BigDecimal(money)));
	}
	
	@ResponseBody
	@GetMapping("test")
	public Result parse(@RequestParam(name="year",defaultValue="2018",required=false)int year,
			@RequestParam(value="month",defaultValue="10",required=false)int month) {
		
		return new Result<>(ResultEnum.OK,moneyService.findMoneyWithMonthReward(year,month));
	}
}
