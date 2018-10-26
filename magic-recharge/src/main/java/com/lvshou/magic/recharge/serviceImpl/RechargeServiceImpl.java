package com.lvshou.magic.recharge.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.recharge.dao.RechargeDao;
import com.lvshou.magic.recharge.entity.Recharge;
import com.lvshou.magic.recharge.service.RechargeService;
import com.lvshou.magic.utils.MainUUID;

@Service
public class RechargeServiceImpl implements RechargeService{

	@Autowired
	RechargeDao rechargeDao;
	
	@Override
	public Recharge findOne(Recharge recharge) {
		// TODO Auto-generated method stub
		if(recharge==null) {
			return null;
		}
		if(recharge.getId()!=null) {
			return rechargeDao.findById(recharge.getId()).orElse(null);
		}
		return null;
	}

	@Override
	public Page<Recharge> findAll(int page,int size) {
		// TODO Auto-generated method stub
		return rechargeDao.findAll(PageRequest.of(page, size));
	}

	@Override
	public Recharge update(Recharge recharge) {
		// TODO Auto-generated method stub
		if(recharge==null) throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);
		Recharge recharge2=findOne(recharge);
		if(recharge2==null) throw new CommonException(ResultEnum.UPDATE_NOT_FIND);
		
		if(recharge.getMoney()!=null) {
			recharge2.setMoney(recharge.getMoney());
		}
		if(recharge.getUserId()!=null) {
			recharge2.setUserId(recharge.getUserId());
		}
		if(recharge.getDescribetion()!=null) {
			recharge2.setDescribetion(recharge.getDescribetion());
		}
		if(recharge.getOperator()!=null) {
			recharge2.setOperator(recharge.getOperator());
		}
		rechargeDao.save(recharge2);
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		try {
			rechargeDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CommonException(ResultEnum.ID_NOT_EXIT);
		}
	}

	@Override
	public Recharge insert(Recharge recharge) {
		// TODO Auto-generated method stub
		if(recharge==null) {
			return null;
		}
		if(recharge.getId()==null) {
			recharge.setId(MainUUID.getUUID());
		}
		if(recharge.getMoney()==null) {
			recharge.setMoney(new BigDecimal(0));
		}
		//每充值一次，就需要更改现金表的值
		rechargeDao.save(recharge);
		
		return recharge;
	}

	@Override
	public int findRechargeAmount() {
		// TODO Auto-generated method stub
		return rechargeDao.findAll().size();
	}

	@Override
	public List<Recharge> findByUserId(String userId) {
		// TODO Auto-generated method stub
		
		return rechargeDao.findUserId(userId);
	}

}
