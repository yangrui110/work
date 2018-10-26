package com.lvshou.magic.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.entity.Menu;

public interface MenuDao extends JpaRepository<Menu, String>{
	
	public Page<Menu> findAll(Pageable pageable);
	public List<Menu> findByType(int type);
	@Query(value="select count(*) from menu",nativeQuery=true)
	public int findMenuAmount();

}
