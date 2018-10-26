package com.lvshou.magic.service;

import java.util.List;

import com.lvshou.magic.entity.Advertise;


public interface AdvertiseService {

	public int findAverAmount();
	public List<Advertise> findAll(int page,int size);
	public Advertise insert(Advertise vo);
	public Advertise update(Advertise vo);
	public void delete(String id);
	public Advertise findById(String id);
	public List<Advertise> findByType(int type,int page,int size);
	public int findByTypeAmount(int type);
}
