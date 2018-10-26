package com.lvshou.magic.money;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.export.ExportMoney;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.operation.entity.Operation;
import com.lvshou.magic.operation.service.OperationService;
import com.lvshou.magic.operation.statics.Constant;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.service.UserService;

@Controller
@RequestMapping("money")
public class MoneyController {

	@Autowired
	MoneyService moneyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ExportMoney exportMoney;
	
	@Autowired
	private OperationService operationService;
	
	@ResponseBody
	@GetMapping("findById")
	public Result find(@RequestParam("id") String id) {
		Money money=new Money();
		money.setId(id);
		return new Result<>(ResultEnum.OK, moneyService.findOne(money));
	}
	
	@GetMapping("lists")
	public ModelAndView finds() {
		return new ModelAndView("money");
	}
	
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(
			@RequestParam(value="numId",required=false)String numId,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(name="monthReward",required=false)String date,
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="rows",defaultValue="10",required=false)int size) throws Exception {
		List<Money> list=null;
		int count =0;
		
		User user=userService.findUser(new User().setNumId(numId).setName(name).setPhone(phone));
		if(user!=null&&user.getId()!=null) {
			String userId=user.getId();
			list=moneyService.findUserId(userId, page-1, size);
			count=moneyService.countUserIds(userId);
		}else {
			list=moneyService.findMoneyWithMonthRewardPage(page,size,date);
			count=moneyService.findMoneyAmount();
		}
		//List<com.lvshou.magic.user.moneyOperate.MoneyVo> lVos=moneyOperate.getData(date, list);
		return new Result<>(ResultEnum.OK,list,count);
	};
	
	@ResponseBody
	@PutMapping("update/{id}")
	public Result update(@PathVariable String id) {
		Money money=new Money();
		money.setId(id);
		money.setBalance(new BigDecimal(1000));
		return new Result<>(ResultEnum.OK, moneyService.update(money));
	}
	
	@ResponseBody
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable String id) {
		moneyService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@ResponseBody
	@PostMapping("insert/{userId}")
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
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<Money> moneys) {
		Iterator<Money> iterator=moneys.iterator();
		while (iterator.hasNext()) {
			Money money =iterator.next();
			moneyService.delete(money.getId());
		}
		return new Result<>(ResultEnum.OK);
	}
	
	/**同意请求*/
	@ResponseBody
	@GetMapping("withdrawAgree")
	public Result agree(@RequestParam("id")String id) {
		moneyService.withDraw(id);
		return new Result<>(ResultEnum.OK);
	}
	/**驳回请求*/
	@ResponseBody
	@GetMapping("withdrawDef")
	public Result def(String id) {
		moneyService.def(id);
		return new Result<>(ResultEnum.OK);
	}
	@GetMapping("withdrawsAgree")
	public ModelAndView withAgree() {
		return new ModelAndView("withdrawsAgree");
	}
	@GetMapping("withdrawsDef")
	public ModelAndView withDef() {
		return new ModelAndView("withdrawsDef");
	}
	@ResponseBody
	@GetMapping("startDraw")
	public Result startDraw(@RequestParam(value="userId",required=true)String userId,@RequestParam(value="money",required=true)String money) {
		return new Result<>(ResultEnum.OK,moneyService.startDraw(userId, new BigDecimal(money)));
	}
	
	@GetMapping("export")
	public void export(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="monthReward",required=false)String date) throws Exception {
		exportMoney.export(request, response, date);
	}
	
	@GetMapping("draw")
	@ResponseBody
	public Result draw() {
		List<Money> moneys=moneyService.findAllNoPage();
		for (Money money : moneys) {
			if(money.getTotalReward().intValue()!=0) {
				Operation operation=new Operation();
				operation.setMoney(money.getTotalReward());
				operation.setStatus(1);
				operation.setOperate(Constant.WITHDRAW);
				operation.setUserId(money.getUserId());
				operation.setDescribetion("");
				operationService.insert(operation);
			}
		}
		return new Result<>(ResultEnum.OK);
	}
}
