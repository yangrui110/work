package com.lvshou.magic.service;

import java.util.List;

import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.vo.CouponVo;

public interface CouponService {

	public int findCouponsAmount();
	public List<Coupon> findAll();
	public Coupon findById(String id);
	public Coupon insert(CouponVo coupon);
	public Coupon update(CouponVo coupon);
	public void delete(String id);
	public List<Coupon> findDefaults();
	
}
