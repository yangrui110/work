package com.lvshou.magic.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.user.entity.OldVip;

public interface OldVipDao extends JpaRepository<OldVip, String> {

	@Query(value="select count(*) from old_vip",nativeQuery=true)
	public int amount();
	
	public List<OldVip> findByName(String name);
	public List<OldVip> findByPhone(String phone);
	public List<OldVip> findByNumId(String numId);
	
	/**根据名字和省份查找*/
	public List<OldVip> findByNameAndPosition(String name,String position);
	public List<OldVip> findByNameAndPhone(String name,String phone);
	
	public List<OldVip> findByReferNameAndReferArea(String name,String area);
	
}
