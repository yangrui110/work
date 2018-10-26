package com.lvshou.magic.user.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lvshou.magic.user.entity.VipHistory;

public interface VipHistoryService {

	/**增加新纪录*/
	public void insert(VipHistory vipHistory);
	/**查询分类具体值*/
	public Page<VipHistory> finds(int month,int type,int year,int page,int size);
	/**查询数量*/
	public int count(int month,int type,int year);
}
