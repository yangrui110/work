package com.lvshou.magic.order.service;

import java.util.List;

import com.lvshou.magic.order.entity.OrderDetail;

public interface OrderDetailService {

	public List<OrderDetail> findAll(int page,int size);
	
	public OrderDetail findOne(OrderDetail detail);
	
	public OrderDetail insert(OrderDetail detail);
	
	public OrderDetail update(OrderDetail detail);
	
	public void delete(String id);
	
	public List<OrderDetail> findByMainId(String id,int page,int size);
	public List<OrderDetail> findAllByMainId(String id);
	public OrderDetail findById(String id);
	public int findOrderDetailAmount();
	public int findByMainIdAmount(String mainId);
}
