package com.lvshou.magic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.entity.Coupon;

public interface CouponDao extends JpaRepository<Coupon, String> {

	@Query(value="select count(*) from coupon order by create_time DESC",nativeQuery=true)
	public int findAllAmount();
	
	public List<Coupon> findByDefaults(int defaults);
}
