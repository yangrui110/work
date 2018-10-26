package com.lvshou.magic.referral.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lvshou.magic.referral.entity.Referral;

public interface ReferralDao extends JpaRepository<Referral, String>{

	public List<Referral> findByParentId(String parentId);
	public List<Referral> findByChildId(String childId);
	public List<Referral> findByUserId(String userId);
	public Referral findByUserIdAndChildId(String userId,String childId);
	public Page<Referral> findAll(Pageable pageable);
	
}
