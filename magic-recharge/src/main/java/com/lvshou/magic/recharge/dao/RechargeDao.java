package com.lvshou.magic.recharge.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.recharge.entity.Recharge;

public interface RechargeDao extends JpaRepository<Recharge, String> {

	public Page<Recharge> findAll(Pageable pageable);
	@Query(value="select * from recharge where user_id=?1 order by create_time DESC",nativeQuery=true)
	public List<Recharge> findUserId(String userId);
}
