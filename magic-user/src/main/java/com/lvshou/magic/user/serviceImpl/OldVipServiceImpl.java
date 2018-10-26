package com.lvshou.magic.user.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.convert.OldVipConvert;
import com.lvshou.magic.user.dao.OldVipDao;
import com.lvshou.magic.user.entity.OldVip;
import com.lvshou.magic.user.entity.OldVipVo;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.OldVipService;
import com.lvshou.magic.utils.MainUUID;

@Service
public class OldVipServiceImpl implements OldVipService{

	@Autowired
	private OldVipDao oldVipDao;
	@Override
	public void insert(OldVip oldVip) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(oldVip.getId()))
			oldVip.setId(MainUUID.getUUID());
		if(!StringUtils.isEmpty(oldVip.getReferName())&&!StringUtils.isEmpty(oldVip.getReferArea())) {
			oldVip.setReferName(oldVip.getReferName());
			oldVip.setReferArea(oldVip.getReferArea());
		}
		oldVipDao.save(oldVip);
	}

	@Override
	public void insertList(List<OldVip> list) {
		// TODO Auto-generated method stub
		for (OldVip oldVip : list) {
			insert(oldVip);
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		oldVipDao.deleteById(id);
	}

	
	@Override
	public void update(OldVip oldVip) {
		// TODO Auto-generated method stub
		OldVip oldVip2=oldVipDao.findById(oldVip.getId()).orElse(null);
		if(oldVip2==null) throw new CommonException("待更新的记录不存在");
		if(!StringUtils.isEmpty(oldVip.getName()))
			oldVip2.setName(oldVip.getName());
		if(!StringUtils.isEmpty(oldVip.getPhone()))
			oldVip2.setPhone(oldVip.getPhone());
		if(oldVip.getPayTime()!=null)
			oldVip2.setPayTime(oldVip.getPayTime());
		if(!StringUtils.isEmpty(oldVip.getClerk()))
			oldVip2.setClerk(oldVip.getClerk());
		if(!StringUtils.isEmpty(oldVip.getPayMethod()))
			oldVip2.setPayMethod(oldVip.getPayMethod());
		if(!StringUtils.isEmpty(oldVip.getBankName()))
			oldVip2.setBankName(oldVip.getBankName());
		if(!StringUtils.isEmpty(oldVip.getBankCard()))
			oldVip2.setBankCard(oldVip.getBankCard());
		if(!StringUtils.isEmpty(oldVip.getPosition()))
			oldVip2.setPosition(oldVip.getPosition());
		if(!StringUtils.isEmpty(oldVip.getReferName()))
			oldVip2.setReferName(oldVip.getReferName());
		if(!StringUtils.isEmpty(oldVip.getReferArea()))
			oldVip2.setReferArea(oldVip.getReferArea());
		if(oldVip.getTotalReward()!=null)
			oldVip2.setTotalReward(oldVip.getTotalReward());
		oldVipDao.save(oldVip2);
	}

	@Override
	public List<OldVip> findAll(int page,int size) {
		// TODO Auto-generated method stub
		Page<OldVip> page2=oldVipDao.findAll(PageRequest.of(page, size));

		return page2.getContent();
	}

	@Override
	public int allAmount() {
		// TODO Auto-generated method stub
		return oldVipDao.amount();
	}

	@Override
	public OldVip findId(String id) {
		// TODO Auto-generated method stub
		return oldVipDao.findById(id).orElse(null);
	}

	@Override
	public List<OldVip> finds(OldVip vips) {
		// TODO Auto-generated method stub
		List<OldVip> lists=null;
		if(!StringUtils.isEmpty(vips.getNumId())) {
			lists=oldVipDao.findByNumId(vips.getNumId());
		}else if (!StringUtils.isEmpty(vips.getName())) {
			lists=oldVipDao.findByName(vips.getName());
		}else if (!StringUtils.isEmpty(vips.getPhone())) {
			lists=oldVipDao.findByPhone(vips.getPhone());
		}
		return lists;
	}

	@Override
	public int[] crossError() {
		// TODO Auto-generated method stub
		List<OldVip> lists=oldVipDao.findByReferNameAndReferArea("", "");
		if (lists.size()<=0) throw new CommonException("数据中没有一级推荐人（父推荐人为空）");
		List<Integer> result=new ArrayList<Integer>();
		for (OldVip oldVip : lists) {
			result.add(Integer.parseInt(oldVip.getNumId()));
			//查找下一级
			findNext(oldVip.getName(), oldVip.getPosition(), result);
		}
		int all=allAmount();
		if(all==result.size()) return null;
		//result排序
		Collections.sort(result, (a,b)->{
			return a.compareTo(b);
		});
		
		List<Integer> rs=new ArrayList<>();
		for (int i = 0; i < result.size(); i++) {
			if(i+1==result.size()) break;
			int start=result.get(i).intValue();
			int end=result.get(i+1).intValue();
			int len=end-start;
			if(len>1)
				for (int j = start+1; j < end; j++) {
					rs.add(j);
				}
		}
		if (result.get(result.size()-1).intValue()<all) {
			for (int j = result.get(result.size()-1).intValue()+1; j <=all; j++) {
				rs.add(j);
			}
		}
		if(result.get(0).intValue()!=1)
			for (int i = 1; i < result.get(0).intValue(); i++) {
				rs.add(i);
			}
		int[] rsm=new int[rs.size()];
		for (int i = 0; i < rsm.length; i++) {
			rsm[i]=rs.get(i).intValue();
		}
		return rsm;
	}

	private void findNext(String parentName,String parentArea,List<Integer> list) {
		List<OldVip> findsList=oldVipDao.findByReferNameAndReferArea(parentName, parentArea);
		if(findsList==null) return ;
		if(findsList.size()<=0) return;
		for (OldVip oldVip : findsList) {
			list.add(Integer.parseInt(oldVip.getNumId()));
			findNext(oldVip.getName(), oldVip.getPosition(), list);
		}
	}
	
	@Override
	public int[] verticalError() {
		// TODO Auto-generated method stub
		List<OldVip> lists=oldVipDao.findAll();
		List<Integer> result=new ArrayList<>();
		for (OldVip oldVip : lists) {
			if(StringUtils.isEmpty(oldVip.getReferName()))
				continue;
			List<OldVip> rs=oldVipDao.findByNameAndPosition(oldVip.getReferName(), oldVip.getReferArea());
			if(rs.size()<=0)
				result.add(Integer.parseInt(oldVip.getNumId()));
		}
		int[] rsm=new int[result.size()];
		for (int i = 0; i < rsm.length; i++) {
			rsm[i]=result.get(i).intValue();
		}
		return rsm;
	}

	@Override
	public List<OldVip> findByNameAndPhone(String name, String phone) {
		// TODO Auto-generated method stub
		return oldVipDao.findByNameAndPhone(name, phone);
	}

	@Override
	public boolean exist(String name, String phone) {
		// TODO Auto-generated method stub
		List<OldVip> lists=oldVipDao.findByNameAndPhone(name, phone);
		if(lists.size()<=0)
			return false;
		return true;
	}

	@Override
	public boolean hasParent(String name, String phone) {
		// TODO Auto-generated method stub
		List<OldVip> lists=oldVipDao.findByNameAndPhone(name, phone);
		if(lists.size()<=0)
			return false;
		if(StringUtils.isEmpty(lists.get(0).getReferName()))
			return false;
		return true;
	}

	@Override
	public OldVip findChildOne(OldVip oldVip,int classes) {
		// TODO Auto-generated method stub
		//classes==0时，
		if(classes==0)
			return null;
		List<OldVip> lists=oldVipDao.findByReferNameAndReferArea(oldVip.getName(), oldVip.getPosition());
		if(lists.size()<5)
			return oldVip;
		else if (lists.size()==5) {
			for (OldVip oldVip2 : lists) {
				return findChildOne(oldVip2, classes--);
			}
		}else 
			throw new CommonException("老系统中用户编号为"+oldVip.getNumId()+"的下一级已经超过5个用户，请仔细核对");
		return null;
	}

	@Override
	public void findAllParent(OldVip oldVip, int classes,List<OldVipVo> lists) {
		// TODO Auto-generated method stub
		if(classes>3)
			return ;
		List<OldVip> oldVips=oldVipDao.findByNameAndPosition(oldVip.getName(),oldVip.getPosition());
		if(oldVips.size()<=0)
			throw new CommonException("用户名："+oldVip.getName()+"用户地址:"+oldVip.getPosition()+"不存在于系统中");
		lists.add(OldVipConvert.convertToVo(oldVips.get(0), classes));
		if(!StringUtils.isEmpty(oldVip.getReferName())&&!StringUtils.isEmpty(oldVip.getReferArea())) {
			oldVip.setName(oldVip.getReferName());
			oldVip.setPosition(oldVip.getReferArea());
			findAllParent(oldVip, classes++, lists);
		}
		return ;
	}
	
	@Override
	public void addTotalRewardById(String id,BigDecimal money) {
		OldVip oldVip=oldVipDao.findById(id).orElse(null);
		if(oldVip==null) throw new CommonException("老系统中更新金额时找不到id为"+id+"的用户");
		oldVip.setTotalReward(oldVip.getTotalReward().add(money));
		oldVipDao.save(oldVip);
	}
	
	
	
}
