package com.lvshou.magic.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.entity.Advertise;

public interface AdvertiseDao extends JpaRepository<Advertise, String>{

	public Page<Advertise> findAll(Pageable pageable);
	
	public List<Advertise> findByType(int type,Pageable pageable);
	public List findByType(int type);
	@Query(value="select count(*) from advertise where type=?1",nativeQuery=true)
	public int findsAllType(int type);
}
