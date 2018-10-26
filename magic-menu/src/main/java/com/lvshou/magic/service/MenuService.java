package com.lvshou.magic.service;

import java.util.List;

import com.lvshou.magic.entity.Menu;

public interface MenuService {

	public int findMenuCount();
	public List<Menu> findAll(int page,int size);
	public List<Menu> findByType(int type);
	public Menu update(Menu menu);
	public Menu findById(String id);
	public Menu insert(Menu menu);
	public void delete(String id);
}
