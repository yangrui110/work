package com.lvshou.magic.money.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.dao.RewardDao;
import com.lvshou.magic.money.entity.Reward;
import com.lvshou.magic.money.service.RewardService;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.utils.MainUUID;

@Service
public class RewardServiceImpl implements RewardService{

	@Autowired
	RewardDao rewardDao;
	
	@Override
	public List<Reward> findByTarget(String target) {
		// TODO Auto-generated method stub
		//直接查询出所有的结果值
		return rewardDao.findByTarget(target);
	}

	@Override
	public Reward update(Reward reward) {
		// TODO Auto-generated method stub
		if(reward.getId()==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		Reward reward2=rewardDao.findById(reward.getId()).orElse(null);
		if(reward.getSource()!=null&&!reward.getSource().equals("")) {
			reward2.setSource(reward.getSource());
		}
		if(reward.getSourcePhone()!=null&&!reward.getSourcePhone().equals("")) {
			reward2.setSourcePhone(reward.getSourcePhone());
		}
		if(reward.getSourceName()!=null&&!reward.getSourceName().equals("")) {
			reward2.setSourceName(reward.getSourceName());
		}
		if(reward.getTargetPhone()!=null&&!reward.getTargetPhone().equals("")) {
			reward2.setTargetPhone(reward.getTargetPhone());
		}
		if(reward.getTarget()!=null&&!reward.getTarget().equals("")) {
			reward2.setTarget(reward.getTarget());
		}
		if(reward.getTargetName()!=null&&!reward.getTargetName().equals("")) {
			reward2.setTargetName(reward.getTargetName());
		}
		if(reward.getCreateTime()!=null)
			reward2.setCreateTime(reward.getCreateTime());
		rewardDao.save(reward2);
		return reward2;
	}

	@Override
	public Reward insert(Reward reward) {
		// TODO Auto-generated method stub
		if(reward==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(reward.getId()==null||reward.getId().equals("")) {
			reward.setId(MainUUID.getUUID());
		}
		rewardDao.save(reward);
		return reward;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		rewardDao.deleteById(id);
	}

	@Override
	public List findByTargetPhone(String phone,int page,int size) {
		// TODO Auto-generated method stub
		return rewardDao.findByTargetPhone(phone);
	}

	@Override
	public List<Reward> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return rewardDao.findAll(PageRequest.of(page, size)).getContent();
	}

	@Override
	public int findRewardAmount() {
		// TODO Auto-generated method stub
		return rewardDao.findRewardAmount();
	}

	@Override
	public List<Reward> findRewards(Reward reward, int page, int size) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(reward.getSourceName())) {
			return rewardDao.findSourceName(reward.getSourceName(), PageRequest.of(page, size)).getContent();		
		}else if (!StringUtils.isEmpty(reward.getSourcePhone())) {
			return rewardDao.findSourcePhone(reward.getSourcePhone(), PageRequest.of(page, size)).getContent();
		}else if (!StringUtils.isEmpty(reward.getTargetName())) {
			return rewardDao.findTargetName(reward.getTargetName(), PageRequest.of(page, size)).getContent();
		}else if (!StringUtils.isEmpty(reward.getTargetPhone())) {
			return rewardDao.findTargetPhone(reward.getTargetPhone(), PageRequest.of(page, size)).getContent();
		}
		return null;
	}

	@Override
	public int counts(Reward reward) {
		// TODO Auto-generated method stub
		int counts=0;
		if(!StringUtils.isEmpty(reward.getSourceName())) {
			counts=rewardDao.countSourceName(reward.getSourceName());
		}else if (!StringUtils.isEmpty(reward.getSourcePhone())) {
			counts=rewardDao.countSourcePhone(reward.getSourcePhone());
		}else if (!StringUtils.isEmpty(reward.getTargetName())) {
			counts=rewardDao.countTargetName(reward.getTargetName());
		}else if (!StringUtils.isEmpty(reward.getTargetPhone())) {
			counts=rewardDao.countTargetPhone(reward.getTargetPhone());
		}
		return counts;
	}

	@Override
	public BigDecimal monthReward(int year,int month,String userId) {
		// TODO Auto-generated method stub
		return rewardDao.countReward(year, month, userId);
	}

	
}
