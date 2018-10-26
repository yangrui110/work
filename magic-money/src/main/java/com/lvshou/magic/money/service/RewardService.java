package com.lvshou.magic.money.service;

import java.math.BigDecimal;
import java.util.List;

import com.lvshou.magic.money.entity.Reward;

public interface RewardService {
	
	public int findRewardAmount();
	public List<Reward> findByTarget(String target);
	public Reward update(Reward reward);
	public Reward insert(Reward reward);
	public void delete(String id);
	public List findByTargetPhone(String phone,int page,int size);
	public List<Reward> findAll(int page,int size);
	/**通过reward查找所有*/
	public List<Reward> findRewards(Reward reward,int page,int size);
	public int counts(Reward reward);
	
	BigDecimal monthReward(int year, int month, String userId);
}
