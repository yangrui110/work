package com.lvshou.magic.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.user.entity.VipHistory;

public interface VipHistoryDao extends JpaRepository<VipHistory, String>{

	@Query(value="select * from vip_history where vip=?1 and date_format(create_time,'%m')=?2 and date_format(create_time,'%Y')=?3",nativeQuery=true)
	public Page<VipHistory> queryVip(int vip,int month,int year,Pageable pageable);
	
	@Query(value="select count(*) from vip_history where vip=?1 and date_format(create_time,'%m')=?2 and date_format(create_time,'%Y')=?3",nativeQuery=true)
	public int count(int vip,int month,int year);
}
