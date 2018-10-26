package com.lvshou.magic.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lvshou.magic.dao.AdvertiseDao;
import com.lvshou.magic.entity.Advertise;
import com.lvshou.magic.service.AdvertiseService;
import com.lvshou.magic.utils.MainUUID;

@Service
public class AdvertiseImpl implements AdvertiseService{

	@Autowired
	private AdvertiseDao advertiseDao;
	
	@Override
	public List<Advertise> findAll(int page, int size) {
		// TODO Auto-generated method stub
		
		return advertiseDao.findAll(PageRequest.of(page, size,Sort.by("sort"))).getContent();
	}

	@Override
	public Advertise insert(Advertise vo) {
		// TODO Auto-generated method stub
		if(vo.getId()==null||vo.getId()=="") {
			vo.setId(MainUUID.getUUID());
		}
		advertiseDao.save(vo);
		return vo;
	}

	@Override
	public Advertise findById(String id) {
		return advertiseDao.findById(id).orElse(null);
	}
	@Override
	public Advertise update(Advertise vo) {
		// TODO Auto-generated method stub
		Advertise advertise=findById(vo.getId());
		if(vo.getPics()!=null) {
			advertise.setPics(vo.getPics());
		}
		if(vo.getDescribetion()!=null) {
			advertise.setDescribetion(vo.getDescribetion());
		}
		if(vo.getCreateTime()!=null) {
			advertise.setCreateTime(vo.getCreateTime());
		}
		if(vo.getSort()!=0) {
			advertise.setSort(vo.getSort());
		}
		if(vo.getType()!=0) {
			advertise.setType(vo.getType());
		}
		advertiseDao.save(advertise);
		return vo;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		advertiseDao.deleteById(id);
	}

	@Override
	public List<Advertise> findByType(int type,int page,int size) {
		// TODO Auto-generated method stub
		return advertiseDao.findByType(type,PageRequest.of(page, size,Sort.by("sort")));
	}

	@Override
	public int findAverAmount() {
		// TODO Auto-generated method stub
		return advertiseDao.findAll().size();
	}

	@Override
	public int findByTypeAmount(int type) {
		// TODO Auto-generated method stub
		return advertiseDao.findsAllType(type);
	}

}
