package com.lvshou.magic.operation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.operation.convert.ExportOperation;
import com.lvshou.magic.operation.entity.Operation;
import com.lvshou.magic.operation.service.OperationService;
import com.lvshou.magic.operation.vo.OperationVo;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.export.ExportUser;
import com.lvshou.magic.user.service.UserService;

@Controller
@RequestMapping("operation")
public class OperationController {

	@Autowired
	OperationService operationService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("find/{id}")
	public Result findOne(@PathVariable String id) {
		
		Operation operation=new Operation();
		operation.setId(id);
		return new Result<>(ResultEnum.OK, operationService.findOne(operation));
	}
	
	@RequestMapping("findAll")
	public Result findAll(@RequestParam(value="userId")String userId) {
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
	
	@GetMapping("withdraws")
	public ModelAndView withdrawList() {
		return new ModelAndView("withdraws");
	}
	
	@ResponseBody
	@GetMapping("withdrawlists")
	public Result withdrawlists(
			@RequestParam(value="status",defaultValue="0",required=false)int status,
			@RequestParam(value="date",required=false)String date,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="numId",required=false)String numId,
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="limit",defaultValue="10",required=false)int size) throws ParseException {
		/*
		List<Operation> lists=operationService.withList(status,page-1, size);
		List<OperationVo> volists=new ArrayList<OperationVo>();
		lists.forEach(op->{
			OperationVo vo=new OperationVo();
			BeanUtils.copyProperties(op, vo);
			User user=userService.findUser(new User().setId(op.getUserId()));
			vo.setUser(user);
			volists.add(vo);
		});
		return new Result<>(ResultEnum.OK,volists,operationService.findByStatusAmount(status));
		*/
		List<OperationVo> vos=null;
		int count=0;
		if(!StringUtils.isEmpty(date)) {
			vos=operationService.findMonth(date, status,page,size);
			count=operationService.countMonth(date, status);
		}else if(!StringUtils.isEmpty(name)) {
			vos=operationService.findNames(name, status,page,size);
			count=operationService.countNames(name, status);
		}else if(!StringUtils.isEmpty(phone)) {
			vos=operationService.findPhone(phone, status,page,size);
			count=operationService.countPhone(phone, status);
		}else if(!StringUtils.isEmpty(numId)) {
			vos=operationService.findNumId(numId, status,page,size);
			count=operationService.countNumId(numId, status);
		}else {
			vos=operationService.finds(status,page,size);
			count=operationService.counts(status);
		}
		return new Result<>(ResultEnum.OK,vos,count);
	}
	
	/**导出数据
	 * @throws Exception */
	@GetMapping("export")
	public void export(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="status",defaultValue="0",required=false)int status,
			@RequestParam(value="date",required=false)String date) throws Exception {
		//根据时间查找所有的数据
		List<OperationVo> vos=null;
		if(!StringUtils.isEmpty(date)){
			vos=operationService.findMonthNoPage(date, status);
		}else vos=operationService.findsNoPage(status);
		new ExportOperation().export(request, response, vos);
	}
	
	
}
