package com.lvshou.magic.service;

import com.lvshou.magic.entity.UserCoupon;

public interface UserCouponService {

	public UserCoupon update(UserCoupon coupon);
	public UserCoupon insert(UserCoupon coupon);
	public void delete(String id);
	public void addDefaults(String userId);
}
