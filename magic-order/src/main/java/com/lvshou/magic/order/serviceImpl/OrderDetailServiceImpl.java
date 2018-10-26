package com.lvshou.magic.order.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.order.dao.OrderDetailDao;
import com.lvshou.magic.order.entity.OrderDetail;
import com.lvshou.magic.order.service.OrderDetailService;
import com.lvshou.magic.utils.MainUUID;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailDao orderDetailDao;
	
	@Override
	public List<OrderDetail> findAll(@RequestParam(value="page",defaultValue="0",required=false)int page, 
			@RequestParam(value="page",defaultValue="10",required=false)int size) {
		// TODO Auto-generated method stub
		
		return orderDetailDao.findAll(PageRequest.of(page, size)).getContent();
	}
	@Override
	public OrderDetail findOne(OrderDetail detail) {
		// TODO Auto-generated method stub
		if(detail==null) {
			return null;
		}
		if(detail.getId()!=null&&!detail.getId().equals("")) {
			return orderDetailDao.findById(detail.getId()).orElse(null);
		}
		return null;
	}

	@Override
	public OrderDetail insert(OrderDetail detail) {
		// TODO Auto-generated method stub
		if(detail==null) {
			return null;
		}
		if(detail.getId()==null||detail.getId().equals("")) {
			detail.setId(MainUUID.getUUID());
		}
		orderDetailDao.save(detail);
		return detail;
	}

	@Override
	public OrderDetail update(OrderDetail detail) {
		// TODO Auto-generated method stub
		if(detail==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		OrderDetail detail2=orderDetailDao.findById(detail.getId()).orElse(null);
		if(detail2==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(detail.getId()!=null&&!detail.getId().equals("")) {
			detail2.setId(detail.getId());
		}
		if(detail.getMainId()!=null&&!detail.getMainId().equals("")) {
			detail2.setMainId(detail.getMainId());
		}
		if(detail.getNum()!=0) {
			detail2.setNum(detail.getNum());
		}
		if(detail.getPrice()!=null) {
			detail2.setPrice(detail.getPrice());
		}
		if(detail.getProductId()!=null) {
			detail2.setProductId(detail.getProductId());
		}
		if(detail.getDescribetion()!=null) {
			detail2.setDescribetion(detail.getDescribetion());
		}
		if(detail.getProductName()!=null) {
			detail2.setProductName(detail.getProductName());
		}
		if(detail.getProductIcon()!=null) {
			detail2.setProductIcon(detail.getProductIcon());
		}
		if(detail.getPics()!=null) {
			detail2.setProductIcon(detail.getProductIcon());
		}
		if(detail.getEvaluate()!=null) {
			detail2.setEvaluate(detail.getEvaluate());
		}
		if(detail.getSinglePrice()!=null) {
			detail2.setSinglePrice(detail.getSinglePrice());
		}
		if(!StringUtils.isEmpty(detail.getMainCreateId())) {
			detail2.setMainCreateId(detail.getMainCreateId());
		}
		orderDetailDao.save(detail2);
		return detail2;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id==null||id.equals("")) {
			throw new CommonException(ResultEnum.ID_NOT_EXIT);
		}
		orderDetailDao.deleteById(id);
	}

	@Override
	public OrderDetail findById(String id) {
		// TODO Auto-generated method stub
		return orderDetailDao.findById(id).orElse(null);
	}
	@Override
	public int findOrderDetailAmount() {
		// TODO Auto-generated method stub
		return orderDetailDao.findAll().size();
	}
	@Override
	public List<OrderDetail> findByMainId(String id, int page, int size) {
		// TODO Auto-generated method stub
		return orderDetailDao.findByMainId(id, PageRequest.of(page, size)).getContent();
	}
	@Override
	public List<OrderDetail> findAllByMainId(String id) {
		// TODO Auto-generated method stub
		return orderDetailDao.findAllByMainId(id);
	}
	@Override
	public int findByMainIdAmount(String mainId) {
		// TODO Auto-generated method stub
		return orderDetailDao.findAllMainIds(mainId);
	}

}
