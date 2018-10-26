package com.lvshou.magic.user.service;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.zxing.WriterException;
import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;

public interface UserService {

	public User findUser(User user);
	public Page<User> findAll(int page,int size);
	public void insert(User user) throws Exception;
	public User update(UserVo user) throws Exception;
	public void delete(String id);
	public Page<User> findByPhone(String phone,int page,int size);
	//public boolean upgrade(String referralCode,String parentCode);
	public Page<User> findUsers(User user,int page,int size);
	public int allUsers();
	public int findAdd(Date date);
	public int findByPhone(String phone);
	public int findByVip(int  vip);
	/**获取用户的优惠券*/
	public List<Coupon> getCoupons(String userId);
	
	public String createShareCode(String prefixString,String userId,String background) throws WriterException, IOException, Exception;
	/**模糊查询name*/
	public int countName(String name);
	/**vipName*/
	public int countVipName(String name);
	
	public List<UserVo> findAllChild(String id);
	public List<User> partners(int page, int size);
	public int countPartners();
	
	public List<User> directors(int page,int size);
	public int countDirectors();
	
	public List<User> findByDirectCode(String directCode);
	public List<User> typeChilds(User user,int type) throws Exception;
	public User bindOldVip(UserVo n);
	public void allChilds(User user, int deep, List<UserVo> lists) throws Exception;

	/***查找所有的用户***/
	public List<User> findAllUser();
	public List<User> findAllByVip(int vip);
	public List<User> findPageAllVips(int vip,int page,int size);
	
	/**未绑定用户的数据*/
	public List<User> findNoIfy(int page,int size);
	public List<User> findNoIfyNoPage();
	public int amountNoIfy();
	public void allParent(User user, int classes, List<UserVo> list);
	public void judgePartner(User user) throws Exception;
	
	public void delBind(String userId);
}
