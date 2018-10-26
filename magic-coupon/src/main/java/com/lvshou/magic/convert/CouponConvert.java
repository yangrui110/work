package com.lvshou.magic.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.BeanUtils;

import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.vo.CouponVo;

public class CouponConvert {

	public static Coupon convertToCoupon(CouponVo vo) throws ParseException {
		Coupon coupon=new Coupon();
		BeanUtils.copyProperties(vo, coupon);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		if(vo.getCreateTime()!=null)
		coupon.setCreateTime(simpleDateFormat.parse(vo.getCreateTime()));
		if(vo.getArriveTime()!=null)
		coupon.setArriveTime(simpleDateFormat.parse(vo.getArriveTime()));
		if(vo.getStartTime()!=null)
		coupon.setStartTime(simpleDateFormat.parse(vo.getStartTime()));
		return coupon;
	}
	public static CouponVo convertToCouponVo(Coupon coupon) {
		CouponVo couponVo=new CouponVo();
		BeanUtils.copyProperties(coupon, couponVo);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		if(coupon.getCreateTime()!=null) 
			couponVo.setCreateTime(simpleDateFormat.format(coupon.getCreateTime()));
		if(coupon.getArriveTime()!=null)
			couponVo.setArriveTime(simpleDateFormat.format(coupon.getArriveTime()));
		if(coupon.getStartTime()!=null)
			couponVo.setStartTime(simpleDateFormat.format(coupon.getStartTime()));
		return couponVo;
	}
}
