package com.lvshou.magic.user.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.user.dao.VipHistoryDao;
import com.lvshou.magic.user.entity.VipHistory;
import com.lvshou.magic.user.service.VipHistoryService;
import com.lvshou.magic.utils.MainUUID;

@Service
public class VipHistoryImpl implements VipHistoryService{

	@Autowired
	private VipHistoryDao vipHistoryDao;
	
	@Override
	public void insert(VipHistory vipHistory) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(vipHistory.getId())) {
			vipHistory.setId(MainUUID.getUUID());
		}
		vipHistoryDao.save(vipHistory);
	}

	@Override
	public Page<VipHistory> finds(int month, int type,int year ,int page, int size) {
		// TODO Auto-generated method stub
		return vipHistoryDao.queryVip(type, month,year, PageRequest.of(page, size)); 
	}

	@Override
	public int count(int month, int type,int year) {
		// TODO Auto-generated method stub
		return vipHistoryDao.count(type, month,year);
	}

}
