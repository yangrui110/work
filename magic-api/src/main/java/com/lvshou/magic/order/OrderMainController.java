package com.lvshou.magic.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.lvshou.magic.config.PayProperties;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.order.service.OrderMainService;
import com.lvshou.magic.order.vo.OrderMainVo;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.util.IpUtils;

@Controller
@RequestMapping("orderMain")
public class OrderMainController {

	@Autowired
	OrderMainService orderMainservice;
	
	@Autowired
	PayProperties payProperties;
	
	@ResponseBody
	@RequestMapping("findAll")
	public Result findAll(@RequestParam(value="orderStatus",defaultValue="0",required=false) int orderStatus,
			@RequestParam(value="payStatus",defaultValue="0",required=false) int payStatus,
			@RequestParam(value="size",defaultValue="0",required=false) int page,
			@RequestParam(value="size",defaultValue="10",required=false) int size) {
		OrderMainVo vo=new OrderMainVo();
		if(orderStatus!=0) {
			vo.setOrderStatus(orderStatus);
		}else if (payStatus!=0) {
			vo.setPayStatus(payStatus);
			
		}
		return new Result<>(ResultEnum.OK, orderMainservice.finds(vo, page, size));
	}
	
	@ResponseBody
	@PutMapping("update")
	public Result update(OrderMainVo detail) {
		return new Result<>(ResultEnum.OK, orderMainservice.update(detail));
	}
	
	@ResponseBody
	@PostMapping("insert")
	public Result insert(@RequestBody OrderMainVo detail) {
		return new Result<>(ResultEnum.OK, orderMainservice.insert(detail));
	}
	
	@ResponseBody
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable("id") String id) {
		orderMainservice.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@ResponseBody
	@PostMapping("createOrder")
	public Result createOrder(@RequestBody OrderMainVo vo) {
		return new Result<>(ResultEnum.OK,orderMainservice.createOrder(vo));
	}
	@GetMapping("orders")
	public ModelAndView find() {
		
		return new ModelAndView("orderList");
	}
	@ResponseBody
	@GetMapping("findById")
	public Result findById(@RequestParam("id")String id) {
		List list=new ArrayList<>();
		list.add(orderMainservice.findById(id));
		return new Result<>(ResultEnum.OK,list);
	}
	@ResponseBody
	@GetMapping("findAllByBuyId")
	public Result findByBuyId(@RequestParam(value="userId",required=true)String userId) {
		return new Result<>(ResultEnum.OK,orderMainservice.findByBuyId(userId));
	}
	@ResponseBody
	@GetMapping("payOrder")
	public Result payOrder(@RequestParam("userId")String userId,
			@RequestParam("id")String id,HttpServletRequest request) {
		
		return new Result<>(ResultEnum.OK,orderMainservice.payOrder(payProperties.getPay_notify(),userId,id,IpUtils.getIpAddr(request)));
	}
	@ResponseBody
	@GetMapping("finishOrder")
	public Result finishOrder(@RequestParam("userId")String userId,@RequestParam("id")String id,HttpServletRequest request) {
		
		return new Result<>(ResultEnum.OK,orderMainservice.payOrder(payProperties.getPay_notify(),userId,id,IpUtils.getIpAddr(request)));
	}
	@ResponseBody
	@GetMapping("findByUserId")
	public Result findByUserId(@RequestParam("userId")String userId) {
		return new Result<>(ResultEnum.OK,orderMainservice.findByUserId(userId));
	}
	
	@ResponseBody
	@GetMapping("findFinished")
	public Result findFinished(@RequestParam("userId")String userId) {
		return new Result<>(ResultEnum.OK,orderMainservice.findFinished(userId));
	}
	
	@ResponseBody
	@GetMapping("findNeeds")
	public Result findNeeds(@RequestParam("userId")String userId) {
		return new Result<>(ResultEnum.OK,orderMainservice.findNeeds(userId));
	}
}
