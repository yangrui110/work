package com.lvshou.magic.order.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lvshou.magic.order.entity.OrderMain;

public interface OrderMainDao extends JpaRepository<OrderMain, String> {

	@Query(value="select * from order_main where pay_status>2 order by create_time ASC",nativeQuery =true)
	public Page<OrderMain> findMains(Pageable pageable);
	@Query(value="select * from order_main where pay_status=?1 order by create_time ASC",nativeQuery =true)
	public Page<OrderMain> findPayStatus(int payStatus,Pageable pageable);
	@Query(value="select * from order_main where order_status=?1 order by create_time ASC",nativeQuery =true)
	public Page<OrderMain> findOrderStatus(int orderStatus,Pageable pageable);
	
	@Query("select id from OrderMain where createTime>?1")
	public List findTimes(Date date);
	@Query(value="select count(*) from order_main where pay_status>2",nativeQuery=true)
	public int findAllOrderMains();
	@Query(value="select count(*) from order_main where pay_status=?1",nativeQuery=true)
	public int findAmount(int payStatus);
	
	@Query(value="select *from order_main where pay_status>2 and buy_id=?2",nativeQuery=true)
	public List<OrderMain> findByBuyId(String buyId);
	@Query(value="select * from order_main where pay_status>2 and user_id=?2",nativeQuery=true)
	public List<OrderMain> findByUserId(String userId);
	
	@Query(value="select * from order_main where buy_name like %?1% and pay_status>2",nativeQuery=true)
	public Page<OrderMain> findName(String name,Pageable pageable);
	@Query(value="select count(*) from order_main where buy_name like %?1% and pay_status>2",nativeQuery=true)
	public int countNames(String name);
	public Page<OrderMain> findAllByCreateId(String createId,Pageable pageable);
	@Query(value="select * from order_main where buy_phone = ?1 and pay_status>2",nativeQuery=true)
	public Page<OrderMain> findAllsBuyPhone(String phone,Pageable pageable);
	@Query(value="select count(*) from order_main where buy_phone=?1 and pay_status>2",nativeQuery=true)
	public int countPhone(String phone);

	@Query(value="select * from order_main where user_id=?1 and pay_status=?2",nativeQuery=true)
	public List<OrderMain> findFinished(String userId,int paystatus);
	
	@Transactional
	@Modifying
	@Query(value="delete a,b from order_main as a left join order_detail as b on a.id = b.main_id where a.id=?1",nativeQuery=true)
	public void deleteOne(String id);
	@Query(value="select * from order_main where user_id=?1 and pay_status>2 order by create_time desc",nativeQuery=true)
	public List<OrderMain> findNeeds(String userId);
}
