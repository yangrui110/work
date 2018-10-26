package com.lvshou.magic.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.dao.CouponDao;
import com.lvshou.magic.dao.UserCouponDao;
import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.entity.UserCoupon;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.service.UserCouponService;
import com.lvshou.magic.statics.Status;
import com.lvshou.magic.utils.MainUUID;

@Service
public class UserCouponServiceImpl implements UserCouponService {

	@Autowired
	private UserCouponDao userCouponDao;
	@Autowired
	private CouponDao couponDao;

	@Override
	public UserCoupon update(UserCoupon coupon) {
		// TODO Auto-generated method stub
		UserCoupon coupon2=userCouponDao.findById(coupon.getId()).orElse(null);
		if(!StringUtils.isEmpty(coupon.getUserId())) {
			coupon2.setUserId(coupon.getUserId());
		}
		if(!StringUtils.isEmpty(coupon.getCouponId())) {
			coupon2.setCouponId(coupon.getCouponId());
		}
		userCouponDao.save(coupon2);
		return coupon2;
	}

	@Override
	public UserCoupon insert(UserCoupon coupon) {
		// TODO Auto-generated method stub
		if(coupon==null) {
			throw new CommonException("UserCouponServiceImpl待插入的UserCoupon为null");
		}
		if(StringUtils.isEmpty(coupon.getId())) {
			coupon.setId(MainUUID.getUUID());
		}
		userCouponDao.save(coupon);
		return coupon;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		userCouponDao.deleteById(id);
	}
	
	public List<Coupon> findCoupons(String userId){
		List<UserCoupon> coupons=userCouponDao.findByUserId(userId);
		List<Coupon> list=new ArrayList<>();
		for (int i = 0; i <coupons.size(); i++) {
			list.add(couponDao.findById(coupons.get(i).getCouponId()).orElse(null));
		}
		return list;
	}

	@Override
	@Transactional
	public void addDefaults(String userId) {
		// TODO Auto-generated method stub
		List<Coupon> lists=couponDao.findByDefaults(Status.YES);
		UserCoupon userCoupon;
		Iterator<Coupon> iterator=lists.iterator();
		while (iterator.hasNext()) {
			Coupon coupon = (Coupon) iterator.next();
			for(int i=0;i<coupon.getNum();i++) {
				userCoupon=new UserCoupon();
				userCoupon.setUserId(userId);
				userCoupon.setCouponId(coupon.getId());
				insert(userCoupon);
			}
		}
		
	}
}
