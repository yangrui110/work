package com.lvshou.magic.user.service;

import java.math.BigDecimal;
import java.util.List;
import com.lvshou.magic.user.entity.OldVip;
import com.lvshou.magic.user.entity.OldVipVo;

public interface OldVipService {

	public void insert(OldVip oldVip);
	public void insertList(List<OldVip> list);
	public void delete(String id);
	public void update(OldVip oldVip);
	public List<OldVip> findAll(int page,int size);
	
	public OldVip findId(String id);
	public int allAmount();
	public List<OldVip> finds(OldVip vips);
	
	/**
	 * 通过手机号和名字查找用户
	 * */
	public List<OldVip> findByNameAndPhone(String name,String phone);
	/**
	 * 横向检错
	 * @return 
	 * */
	public int[] crossError();
	/**
	 * 纵向检错
	 * */
	public int[] verticalError();
	/**判断用户是否存在*/
	public boolean exist(String name,String phone);
	/**判断用户是否有上一级*/
	public boolean hasParent(String name,String phone);
	/**找出该用户某个未满的下级*/
	public OldVip findChildOne(OldVip oldVip,int classes);
	/**该用户的所有上级*/
	public void findAllParent(OldVip oldVip, int classes, List<OldVipVo> lists);
	public void addTotalRewardById(String id, BigDecimal money);
}
