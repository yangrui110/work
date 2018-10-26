package com.lvshou.magic.account.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lvshou.magic.account.entity.BuyInfo;

public interface BuyInfoDao extends JpaRepository<BuyInfo, String> {
	
	public Page<BuyInfo> findAll(Pageable pageable);
	
	public BuyInfo findByUserId(String userId);

}
