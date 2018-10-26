package com.lvshou.magic.account.service;

import java.util.List;

import com.lvshou.magic.account.entity.BuyInfo;

public interface BuyInfoService {

	public List findAll(int page,int size);
	
	public BuyInfo findOne(BuyInfo buyInfo);
	
	public BuyInfo update(BuyInfo buyInfo);
	
	public BuyInfo insert(BuyInfo buyInfo);
	
	public void delete(String id);
	
	public BuyInfo findById(String id);
	public int findBuyInfoAmount();

	BuyInfo findByUserId(String userId);
}
