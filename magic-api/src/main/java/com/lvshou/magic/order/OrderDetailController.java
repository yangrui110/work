package com.lvshou.magic.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.order.entity.OrderDetail;
import com.lvshou.magic.order.service.OrderDetailService;
import com.lvshou.magic.result.Result;

@Controller
@RequestMapping("orderDetail")
public class OrderDetailController {

	@Autowired
	OrderDetailService orderDetailService;
	
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(@RequestParam(value="size",defaultValue="0",required=false) int page,
			@RequestParam(value="size",defaultValue="10",required=false) int size) {
		return new Result<>(ResultEnum.OK, orderDetailService.findAll(page, size));
	}
	
	@ResponseBody
	@PutMapping("update")
	public Result update(OrderDetail detail) {
		return new Result<>(ResultEnum.OK, orderDetailService.update(detail));
	}
	
	@ResponseBody
	@PostMapping("insert")
	public Result insert(OrderDetail detail) {
		return new Result<>(ResultEnum.OK, orderDetailService.insert(detail));
	}
	
	@ResponseBody
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable("id") String id) {
		orderDetailService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@ResponseBody
	@GetMapping("findById")
	public Result findById(@RequestParam("id")String id) {
		List list=new ArrayList<>();
		list.add(orderDetailService.findById(id));
		return new Result<>(ResultEnum.OK,list);
	}
	@ResponseBody
	@GetMapping("finds")
	public Result finds(@RequestParam("mainId") String mainId) {
		return new Result<>(ResultEnum.OK, orderDetailService.findAllByMainId(mainId));
	}
}
