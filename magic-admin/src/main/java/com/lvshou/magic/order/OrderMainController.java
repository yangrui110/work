package com.lvshou.magic.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
import com.lvshou.magic.order.service.OrderMainService;
import com.lvshou.magic.order.vo.OrderMainVo;
import com.lvshou.magic.result.Result;

@Controller
@RequestMapping("orderMain")
public class OrderMainController {

	
	@Autowired
	OrderMainService orderMainservice;
	
	@ResponseBody
	@RequestMapping("findAll")
	public Result findAll(@RequestParam(value="orderStatus",defaultValue="0",required=false) int orderStatus,
			@RequestParam(value="payStatus",defaultValue="0",required=false) int payStatus,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="createId",required=false)String createId,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="page",defaultValue="1",required=false) int page,
			@RequestParam(value="size",defaultValue="10",required=false) int size) {
		OrderMainVo vo=new OrderMainVo();
		vo.setBuyPhone(phone);vo.setBuyName(name);vo.setCreateId(createId);
		vo.setPayStatus(payStatus);
		vo.setOrderStatus(orderStatus);
		/**按照条件查找所有的订单*/
		int counts=0;
		List list=orderMainservice.finds(vo, page-1, size);
		if(!StringUtils.isEmpty(phone)) {
			counts=orderMainservice.countPhone(phone);
		}else if (!StringUtils.isEmpty(name)) {
			counts=orderMainservice.countName(name);
		}else if (!StringUtils.isEmpty(createId)) {
			counts=list.size();
		}else {
			counts=orderMainservice.allOrders(payStatus);
		}
		return new Result<>(ResultEnum.OK, list,counts);
	}
	
	@ResponseBody
	@PutMapping("update")
	public Result update(OrderMainVo detail) {
		return new Result<>(ResultEnum.OK, orderMainservice.update(detail));
	}
	@ResponseBody
	@PostMapping("insert")
	public Result insert(@RequestBody OrderMainVo detail) {
		OrderMainVo vo=orderMainservice.insert(detail);
		return new Result<>(ResultEnum.OK, vo);
	}
	@ResponseBody
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<OrderMainVo> orderMainVos) {
		orderMainVos.forEach(vo->{
			orderMainservice.delete(vo.getId());
		});
		return new Result<>(ResultEnum.OK);
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
		orderMainservice.createOrder(vo);
		return new Result<>(ResultEnum.OK);
	}
	@GetMapping("orders")
	public ModelAndView find(@RequestParam("payStatus")int payStatus,Map<String,Object>map) {
		map.put("payStatus",payStatus );
		return new ModelAndView("orderList",map);
	}

	@ResponseBody
	@GetMapping("finishOrder")
	public Result finishOrder(@RequestParam("id")String id) {
		orderMainservice.finishOrder(id);
		return new Result<>(ResultEnum.OK,new ModelMap("result",true));
	}
	@ResponseBody
	@GetMapping("findById")
	public Result findById(@RequestParam("id")String id) {
		List list=new ArrayList<>();
		list.add(orderMainservice.findById(id));
		return new Result<>(ResultEnum.OK,list);
	}
}
