package com.lvshou.magic.account.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lvshou.magic.account.dao.BuyInfoDao;
import com.lvshou.magic.account.entity.BuyInfo;
import com.lvshou.magic.account.service.BuyInfoService;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.utils.MainUUID;

@Service
public class BuyInfoServiceImpl implements BuyInfoService{

	@Autowired
	BuyInfoDao buyInfoDao;
	
	@Override
	public List findAll(int page, int size) {
		// TODO Auto-generated method stub
		return buyInfoDao.findAll(PageRequest.of(page, size)).getContent();
	}

	@Override
	public BuyInfo findOne(BuyInfo buyInfo) {
		// TODO Auto-generated method stub
		if(buyInfo==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(buyInfo.getId()!=null&&!buyInfo.getId().equals("")) {
			return buyInfoDao.findById(buyInfo.getId()).orElse(null);
		}
		return null;
	}

	@Override
	public BuyInfo update(BuyInfo buyInfo) {
		// TODO Auto-generated method stub
		if(buyInfo==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		BuyInfo buyInfo2=buyInfoDao.findById(buyInfo.getId()).orElse(null);
		if(buyInfo2==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(buyInfo.getName()!=null) {
			buyInfo2.setName(buyInfo.getName());
		}
		if(buyInfo.getPhone()!=null){
			buyInfo2.setPhone(buyInfo.getPhone());
		}
		if(buyInfo.getProvince()!=null) {
			buyInfo2.setProvince(buyInfo.getProvince());
		}
		if(buyInfo.getAddress()!=null) {
			buyInfo2.setAddress(buyInfo.getAddress());
		}
		if(buyInfo.getCity()!=null) {
			buyInfo2.setCity(buyInfo.getCity());
		}
		if(buyInfo.getCountry()!=null) {
			buyInfo2.setCountry(buyInfo.getCountry());
		}
		if(buyInfo.getDefaults()!=0) {
			buyInfo2.setDefaults(buyInfo.getDefaults());
		}
		if(buyInfo.getUserId()!=null&&buyInfo.getUserId()!="") {
			buyInfo2.setUserId(buyInfo.getUserId());
		}
		buyInfoDao.save(buyInfo2);
		return buyInfo2;
	}

	@Override
	public BuyInfo insert(BuyInfo buyInfo) {
		// TODO Auto-generated method stub
		if(buyInfo==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(buyInfo.getId()==null||buyInfo.getId().equals("")) {
			buyInfo.setId(MainUUID.getUUID());
		}
		buyInfoDao.save(buyInfo);
		return buyInfo;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id==null||id.equals("")) {
			throw new CommonException(ResultEnum.ID_NOT_EXIT);
		}
		buyInfoDao.deleteById(id);
	}

	@Override
	public BuyInfo findById(String id) {
		// TODO Auto-generated method stub
		return buyInfoDao.findById(id).orElse(null);
	}

	@Override
	public int findBuyInfoAmount() {
		// TODO Auto-generated method stub
		return buyInfoDao.findAll().size();
	}
	@Override
	public BuyInfo findByUserId(String userId) {
		return buyInfoDao.findByUserId(userId);
	}

	
}
