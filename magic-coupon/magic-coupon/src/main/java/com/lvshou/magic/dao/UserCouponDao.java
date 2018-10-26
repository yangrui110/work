package com.lvshou.magic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvshou.magic.entity.UserCoupon;

public interface UserCouponDao extends JpaRepository<UserCoupon, String> {

	public List<UserCoupon> findByUserId(String userId);
}
