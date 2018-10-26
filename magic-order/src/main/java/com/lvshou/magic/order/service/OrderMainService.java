package com.lvshou.magic.order.service;

import java.util.Date;
import java.util.List;

import com.lvshou.magic.order.entity.OrderMain;
import com.lvshou.magic.order.vo.OrderMainVo;


public interface OrderMainService {
	
	public List findAll(int page,int size);
	
	public OrderMainVo findOne(OrderMainVo orderMain);
	
	public OrderMainVo update(OrderMainVo orderMain);
	
	public OrderMainVo insert(OrderMainVo orderMain);
	
	public void delete(String id);

	public OrderMainVo createOrder(OrderMainVo orderMain);

	public OrderMainVo findById(String id);

	public List<OrderMainVo> finds(OrderMainVo vo, int page, int size);
	
	public String payOrder(String notify,String userId,String id,String ip);
	
	public boolean finishOrder(String id);
	
	public int allOrders(int payStatus);
	
	public int findAdd(Date date);
	public List<OrderMain> findByBuyId(String buyId);

	List<OrderMain> findByUserId(String userId);
	/**查找数量*/
	public int countName(String name);
	public int countPhone(String phone);
	
	/**查找已经完成支付的订单*/
	public List<OrderMain> findFinished(String userId);

	boolean havePayOrder(String id);
	/**查找需要的订单，包括已支付和已完成的订单*/
	public List<OrderMain> findNeeds(String userId);
}
