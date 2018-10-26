package com.lvshou.magic.serviceImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.convert.CouponConvert;
import com.lvshou.magic.dao.CouponDao;
import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.service.CouponService;
import com.lvshou.magic.statics.Status;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.vo.CouponVo;

@Service
public class CouponServiceImpl implements CouponService{

	@Autowired
	private CouponDao couponDao;
	
	@Override
	public List<Coupon> findAll() {
		// TODO Auto-generated method stub
		return couponDao.findAll();
	}

	@Override
	public Coupon insert(CouponVo couponVo) {
		// TODO Auto-generated method stub
		Coupon coupon=null;
		try {
			coupon = CouponConvert.convertToCoupon(couponVo);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommonException("CouponService的insert的插入转换异常");
		}
		if(coupon==null) {
			return null;
		}
		if(StringUtils.isEmpty(coupon.getId())) {
			coupon.setId(MainUUID.getUUID());
		}
		if(coupon.getPrice()==null) {
			coupon.setPrice(new BigDecimal(0));
		}
		if(coupon.getWorth()==null) {
			coupon.setWorth(new BigDecimal(0));
		}
		if(coupon.getVipPrice()==null) {
			coupon.setVipPrice(coupon.getPrice());
		}
		couponDao.save(coupon);
		return coupon;
	}

	@Override
	public Coupon update(CouponVo couponVo) {
		// TODO Auto-generated method stub
		Coupon coupon;
		try {
			coupon = CouponConvert.convertToCoupon(couponVo);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommonException("CouponService的update的更新转换异常");
		}
		Coupon coupon2=couponDao.findById(coupon.getId()).orElse(null);
		if(coupon2==null) {
			throw new CommonException("CouponService未查找到优惠券信息");
		}
		if(coupon.getPrice()!=null) {
			coupon2.setPrice(coupon.getPrice());
		}
		if(coupon.getWorth()!=null) {
			coupon2.setWorth(coupon.getWorth());
		}
		if(coupon.getArriveTime()!=null) {
			coupon2.setArriveTime(coupon.getArriveTime());
		}
		if(coupon.getStartTime()!=null) {
			coupon2.setStartTime(coupon.getStartTime());
		}
		if(coupon.getDescribetion()!=null) {
			coupon2.setDescribetion(coupon.getDescribetion());
		}
		if(coupon.getTitle()!=null) {
			coupon2.setTitle(coupon.getTitle());
		}
		if(coupon.getDefaults()!=0) {
			coupon2.setDefaults(coupon.getDefaults());
		}
		if(coupon.getNum()!=0) {
			coupon2.setNum(coupon.getNum());
		}
		couponDao.save(coupon2);
		return coupon2;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		couponDao.deleteById(id);
	}

	@Override
	public int findCouponsAmount() {
		// TODO Auto-generated method stub
		return couponDao.findAllAmount();
	}

	@Override
	public Coupon findById(String id) {
		// TODO Auto-generated method stub
		return couponDao.findById(id).orElse(null);
	}

	@Override
	public List<Coupon> findDefaults() {
		// TODO Auto-generated method stub
		return couponDao.findByDefaults(Status.YES);
	}
	

}
