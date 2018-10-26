package com.lvshou.magic.referral.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lvshou.magic.referral.entity.Referral;
import com.lvshou.magic.referral.vo.ReferralVo;

public interface ReferralService {

	public int findReferralAmount();
	public Referral findOne(String id);
	
	public Referral findParentOne(String userId);
	
	public List<ReferralVo> findAllParent(String userId);
	
	public List<ReferralVo> findAllChild(String userId);
	
	public Referral insert(Referral referral);
	
	public Referral update(Referral referral);
	
	public Page<Referral> findAll(int page,int size);
	
	public void delete(String id);

	public Referral findChildOne(String userId);

	public List<Referral> findByUserId(String userId);
	public List<Referral> findByParentId(String parentId);
	public Referral findByChildId(String childId);
	
}
