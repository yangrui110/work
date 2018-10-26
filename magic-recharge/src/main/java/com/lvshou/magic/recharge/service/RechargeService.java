package com.lvshou.magic.recharge.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lvshou.magic.recharge.entity.Recharge;

public interface RechargeService {

	public int findRechargeAmount();
	public Recharge findOne(Recharge recharge);
	public Page<Recharge> findAll(int page,int size);
	public Recharge update(Recharge recharge);
	public void delete(String id);
	public Recharge insert(Recharge recharge);
	
	public List<Recharge> findByUserId(String userId);
}
