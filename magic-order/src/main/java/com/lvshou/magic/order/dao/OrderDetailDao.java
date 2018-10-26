package com.lvshou.magic.order.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.order.entity.OrderDetail;

public interface OrderDetailDao extends JpaRepository<OrderDetail, String>{
	
	public Page<OrderDetail> findAll(Pageable pageable);
	
	public List<OrderDetail> findAllByMainId(String id);
	public Page<OrderDetail> findByMainId(String mainId,Pageable pageable);
	@Query(value="select count(*) from order_detail where main_id=?1",nativeQuery=true)
	public int findAllMainIds(String main_id);

}
