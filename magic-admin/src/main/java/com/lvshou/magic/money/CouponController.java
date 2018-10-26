package com.lvshou.magic.money;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lvshou.magic.base.vo.PagedVo;
import com.lvshou.magic.convert.CouponConvert;
import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.service.CouponService;
import com.lvshou.magic.vo.CouponVo;

import javassist.expr.NewArray;

@Controller
@RequestMapping("coupon")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@ResponseBody
	@GetMapping("insert")
	public Coupon insert() {
		Coupon coupon =new Coupon();
		Calendar calendar=Calendar.getInstance();
		coupon.setStartTime(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		coupon.setArriveTime(calendar.getTime());
		coupon.setDescribetion("很好");
		coupon.setPrice(new BigDecimal(10));
		coupon.setTitle("夏季优惠券");
		coupon.setWorth(new BigDecimal(10000));
		coupon=couponService.insert(CouponConvert.convertToCouponVo(coupon));
		return coupon;
	}
	
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(){
		return new Result<>(ResultEnum.OK,couponService.findAll(),couponService.findCouponsAmount());
	}
	@GetMapping("update")
	public Coupon update(@RequestParam("id")String id) {
		Coupon coupon=new Coupon();
		coupon.setId(id);
		coupon.setPrice(new BigDecimal(200));
		coupon.setTitle("修改标题");
		coupon=couponService.update(CouponConvert.convertToCouponVo(coupon));
		return coupon;
	}
	@GetMapping("delete/{id}")
	public void delete(@PathVariable("id")String id) {
		couponService.delete(id);
	}
	@GetMapping("editor")
	public ModelAndView editor(@RequestParam("id")String id,Map<String, Object> map) {
		Coupon coupon=couponService.findById(id);
		map.put("coupon", coupon);
		return new ModelAndView("couponsEditor", map);
	}
	@PostMapping("save")
	public ModelAndView save(CouponVo couponVo) {
		System.out.println(JSONObject.toJSONString(couponVo));
		if(couponVo==null) {
			throw new CommonException("保存/修改coupon时出错\ncoupon="+JSONObject.toJSONString(couponVo));
		}
		if(StringUtils.isEmpty(couponVo.getId())) {
			couponService.insert(couponVo);
		}else {
			couponService.update(couponVo);
		}
		return new ModelAndView("couponsList");
	}
	@GetMapping("add")
	public ModelAndView add() {
		Map map=new HashMap<>();
		
		map.put("coupon", new Coupon());
		return new ModelAndView("couponsEditor",map);
	}
	@GetMapping("finds")
	public ModelAndView finds() {
		return new ModelAndView("couponsList");
	}
	@ResponseBody
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<Coupon> list) {
		Iterator<Coupon> iterator=list.iterator();
		while (iterator.hasNext()) {
			Coupon coupon=iterator.next();
			couponService.delete(coupon.getId());
		}
		return new Result<>(ResultEnum.OK);
	}
}
