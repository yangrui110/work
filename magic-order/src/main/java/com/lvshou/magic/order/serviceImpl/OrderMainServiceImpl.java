package com.lvshou.magic.order.serviceImpl;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lvshou.magic.account.entity.BuyInfo;
import com.lvshou.magic.account.service.BuyInfoService;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.order.convert.OrderMainConvert;
import com.lvshou.magic.order.dao.OrderMainDao;
import com.lvshou.magic.order.entity.OrderDetail;
import com.lvshou.magic.order.entity.OrderMain;
import com.lvshou.magic.order.mainEnum.OrderStatus;
import com.lvshou.magic.order.service.OrderDetailService;
import com.lvshou.magic.order.service.OrderMainService;
import com.lvshou.magic.order.vo.OrderMainVo;
import com.lvshou.magic.payservice.Payment;
import com.lvshou.magic.product.constant.SellConstant;
import com.lvshou.magic.product.convert.ProductConvert;
import com.lvshou.magic.product.entity.Product;
import com.lvshou.magic.product.service.ProductService;
import com.lvshou.magic.product.vo.ProductVo;
import com.lvshou.magic.user.dao.UserDao;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.utils.Convert;
import com.lvshou.magic.utils.MainUUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderMainServiceImpl implements OrderMainService{

	@Autowired
	OrderMainDao orderMainDao;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	ProductService productService;
	@Autowired
	BuyInfoService buyInfoService;
	@Autowired
	private Payment payment;
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<OrderMainVo> findAll(int page, int size) {
		// TODO Auto-generated method stub
		List<OrderMain> list=orderMainDao.findMains(PageRequest.of(page, size)).getContent();
		List<OrderMainVo> vo=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			OrderMainVo vo2=new OrderMainVo();
			/*List<OrderDetail> details=orderDetailService.findAllByMainId(list.get(i).getId());
			if(details==null) {
				throw new CommonException(ResultEnum.ERROR);
			}*/
			/*vo2=OrderMainConvert.convertToVo(list.get(i));
			OrderDetail detail=orderDetailService.findById("388ac30f35df4809ac09129f0f40217c");
			List<OrderDetail> list2=new ArrayList<>();
			list2.add(detail);
			*/
			vo2.setOrderDetails(list.get(i).getOrderDetails());
			//加入链表中
			vo.add(vo2);
		}
		return vo;
	}

	@Override
	public OrderMainVo findOne(OrderMainVo orderMain) {
		// TODO Auto-generated method stub
		if(orderMain==null) {
			log.info("80行，orderMain是null值");
			throw new CommonException(ResultEnum.ERROR);
		}
		if(orderMain.getId()!=null&&!orderMain.getId().equals("")) {
			
			return OrderMainConvert.convertToVo(orderMainDao.findById(orderMain.getId()).orElse(null));
		}
		return null;
	}

	@Override
	public OrderMainVo update(OrderMainVo orderMain) {
		// TODO Auto-generated method stub
		if(orderMain==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		OrderMain orderMain2=orderMainDao.findById(orderMain.getId()).orElse(null);
		if(orderMain2==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(orderMain.getBuyId()!=null&&!orderMain.getBuyId().equals("")) {
			orderMain2.setBuyId(orderMain.getBuyId());
		}
		if(orderMain.getDescribetion()!=null&&!orderMain.getDescribetion().equals("")) {
			orderMain2.setDescribetion(orderMain.getDescribetion());
		}
		if(orderMain.getPayStatus()!=0) {
			orderMain2.setPayStatus(orderMain.getPayStatus());
		}
		if(orderMain.getOrderStatus()!=0) {
			orderMain2.setOrderStatus(orderMain.getOrderStatus());
		}
		if(orderMain.getTotalPrice()!=null) {
			orderMain2.setTotalPrice(orderMain.getTotalPrice());
		}
		if(orderMain.getBuyName()!=null) {
			orderMain2.setBuyName(orderMain.getBuyName());
		}
		if(orderMain.getBuyPhone()!=null) {
			orderMain2.setBuyPhone(orderMain.getBuyPhone());
		}
		if(orderMain.getBuyAddress()!=null) {
			orderMain2.setBuyAddress(orderMain.getBuyAddress());
		}
		if(orderMain.getBuyProvince()!=null) {
			orderMain2.setBuyProvince(orderMain.getBuyProvince());
		}
		if(orderMain.getBuyCity()!=null) {
			orderMain2.setBuyAddress(orderMain.getBuyAddress());
		}
		if(orderMain.getBuyCountry()!=null) {
			orderMain2.setBuyCountry(orderMain.getBuyCountry());
		}
		orderMainDao.save(orderMain2);
		return OrderMainConvert.convertToVo(orderMain2);
	}

	@Override
	public OrderMainVo insert(OrderMainVo orderMain) {
		// TODO Auto-generated method stub
		if(orderMain==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(orderMain.getId()==null||orderMain.getId().equals("")) {
			orderMain.setId(MainUUID.getUUID());
		}
		orderMainDao.save(OrderMainConvert.convertToMain(orderMain));
		return orderMain;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id==null||id.equals("")) {
			throw new CommonException(ResultEnum.ID_NOT_EXIT);
		}
		//orderMainDao.deleteById(id);
		orderMainDao.deleteOne(id);
	}
	
	//创建新订单时，向主表和详情表都插入数据
	@Transactional
	@Override
	public OrderMainVo createOrder(OrderMainVo orderMain) {
		if(orderMain==null) {
			throw new CommonException(ResultEnum.ERROR);
			
		}
		if(orderMain.getId()==null||orderMain.getId().equals("")) {
			
			orderMain.setId(MainUUID.getUUID());
		}
		orderMain.setCreateId(""+System.currentTimeMillis());
		//1查找该订单是否已经存在，如果存在，就直接返回
		OrderMain orderMain2=orderMainDao.findById(orderMain.getId()).orElse(null);
		if(orderMain2!=null) {return orderMain;}
		
		//2.向订单详情表中插入数据
		List<OrderDetail> orders=orderMain.getOrderDetails();
		if(orders==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		orderMain.setPayStatus(OrderStatus.CREATE);
		//设置买家信息	
		BuyInfo info=buyInfoService.findById(orderMain.getBuyId());
		orderMain.setBuyName(info.getName());
		orderMain.setBuyPhone(info.getPhone());
		orderMain.setBuyProvince(info.getProvince());
		orderMain.setBuyCity(info.getCity());
		orderMain.setBuyCountry(info.getCountry());
		orderMain.setBuyAddress(info.getAddress());
		orderMain.setUserId(info.getUserId());
		//设置完毕
		BigDecimal sum=new BigDecimal(0);
		for(int i=0;i<orders.size();i++) {
			//订单的总价，是在服务器决定的，所以应该先查询，再将总价添加进去

			//同时也应该设置好订单主表id
			//查询商品单价
			OrderDetail detail=orders.get(i);
			detail.setMainId(orderMain.getId());
			ProductVo productVo=productService.findOne(ProductConvert.convertToVo(new Product().setId(detail.getProductId())));
			//查询产品库存，如果购买量多于库存，报错
			int num=productVo.getStock();
			if(num<detail.getNum()) {throw new CommonException(ResultEnum.PRODUCT_NOT_ENOUGH);}
			if(productVo.getStatus().intValue()==SellConstant.UNDERCARRIIAGE.intValue()) {throw new CommonException(ResultEnum.XIAJIA);}
			//System.out.println(productVo.getPrice());
			detail.setPrice(productVo.getVipPrice().multiply(new BigDecimal(detail.getNum())));
			detail.setProductName(productVo.getName());
			detail.setEvaluate(productVo.getEvaluate());
			detail.setPics(Convert.listToString(productVo.getPics()));
			detail.setProductIcon(productVo.getIcon());
			detail.setSinglePrice(productVo.getVipPrice());
			detail.setMainCreateId(orderMain.getCreateId());
			orderDetailService.insert(detail);
			sum=sum.add(detail.getPrice());
		}
		//3.不存在该订单时，先向主表中插入数据，可能会增加总金额
		orderMain.setTotalPrice(sum);
		insert(orderMain);
		//订单创建完毕
		return orderMain;
	}

	@Override
	public List<OrderMainVo> finds(OrderMainVo vo,int page,int size) {
		List<OrderMainVo> list;
		if(vo.getPayStatus()!=0) {
			list=OrderMainConvert.convertList(orderMainDao.findPayStatus(vo.getPayStatus(), PageRequest.of(page, size)).getContent());
			for(int i=0;i<list.size();i++) {
				list.get(i).setOrderDetails(orderDetailService.findAllByMainId(list.get(i).getId()));
			}
		}else if(vo.getOrderStatus()!=0) {
			list=OrderMainConvert.convertList(orderMainDao.findOrderStatus(vo.getOrderStatus(), PageRequest.of(page, size)).getContent());
			for(int i=0;i<list.size();i++) {
				list.get(i).setOrderDetails(orderDetailService.findAllByMainId(list.get(i).getId()));
			}
		}else if (!StringUtils.isEmpty(vo.getBuyName())) {
			list=OrderMainConvert.convertList(orderMainDao.findName(vo.getBuyName(),PageRequest.of(page, size)).getContent());
		}else if (!StringUtils.isEmpty(vo.getBuyPhone())) {
			list=OrderMainConvert.convertList(orderMainDao.findAllsBuyPhone(vo.getBuyPhone(),PageRequest.of(page, size)).getContent());
		}else if (!StringUtils.isEmpty(vo.getCreateId())) {
			list=OrderMainConvert.convertList(orderMainDao.findAllByCreateId(vo.getCreateId(),PageRequest.of(page, size)).getContent());
		}
		else {
			list=OrderMainConvert.convertList(orderMainDao.findMains(PageRequest.of(page, size)).getContent());
			/**
			for(int i=0;i<list.size();i++) {
				//orderDetailService.findAllByMainId(list.get(i).getId())
				list.get(i).setOrderDetails(list.get(i).getOrderDetails());
			}**/
		}
		return list;
	}
	
	
	@Override
	public List<OrderMain> findNeeds(String userId) {
		// TODO Auto-generated method stub
		return orderMainDao.findNeeds(userId);
	}

	@Override
	public OrderMainVo findById(String id) {
		// TODO Auto-generated method stub
		OrderMain main=orderMainDao.findById(id).orElse(null);
		return OrderMainConvert.convertToVo(main);
	}

	@Override
	public String payOrder(String notify,String userId,String id,String ip) {
		// TODO Auto-generated method stub
		OrderMain orderMain=orderMainDao.findById(id).orElse(null);
		if(orderMain==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(orderMain.getPayStatus()==OrderStatus.CREATE) {
			orderMain.setPayStatus(OrderStatus.WATTING);
		}
		orderMainDao.save(orderMain);
		User user=userDao.findById(userId).orElse(null);
		if(user==null) {
			LoggerFactory.getLogger(OrderDetailServiceImpl.class).error("用户信息不合法"+userId);
			throw new CommonException("[com.lvshou.magic.order.serviceImpl.OrderMainServiceImpl.payOrder] 用户信息不合法"+userId);
		}
		//开始支付订单
		//价格：orderMain.getTotalPrice().intValue()*100
		try {
			return payment.sign(notify,user.getWid(),id,""+orderMain.getTotalPrice().intValue()*100, ip);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean finishOrder(String id) {
		// TODO Auto-generated method stub
		OrderMain orderMain=orderMainDao.findById(id).orElse(null);
		if(orderMain==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(orderMain.getPayStatus()==OrderStatus.PAIED) {
			orderMain.setPayStatus(OrderStatus.FINISH);
		}
		orderMainDao.save(orderMain);
		return true;
	}
	@Override
	public boolean havePayOrder(String id) {
		// TODO Auto-generated method stub
		OrderMain orderMain=orderMainDao.findById(id).orElse(null);
		if(orderMain==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(orderMain.getPayStatus()==OrderStatus.WATTING) {
			orderMain.setPayStatus(OrderStatus.PAIED);
		}
		orderMainDao.save(orderMain);
		return true;
	}
	@Override
	public int allOrders(int payStatus) {
		// TODO Auto-generated method stub
		if(payStatus==0) {
			return orderMainDao.findAllOrderMains();
		}
		return orderMainDao.findAmount(payStatus);
	}

	@Override
	public int findAdd(Date date) {
		// TODO Auto-generated method stub
		return orderMainDao.findTimes(date).size();
	}
	@Override
	public List<OrderMain> findByBuyId(String buyId){
		return orderMainDao.findByBuyId(buyId);
	}

	@Override
	public List<OrderMain> findByUserId(String userId){
		return orderMainDao.findByUserId(userId);
	}

	@Override
	public int countName(String name) {
		// TODO Auto-generated method stub
		return orderMainDao.countNames(name);
	}

	@Override
	public int countPhone(String phone) {
		// TODO Auto-generated method stub
		return orderMainDao.countPhone(phone);
	}

	@Override
	public List<OrderMain> findFinished(String userId) {
		// TODO Auto-generated method stub
		return orderMainDao.findFinished(userId, OrderStatus.FINISH);
	}
	
	
}
