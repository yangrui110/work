package com.lvshou.magic.referral.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.referral.dao.ReferralDao;
import com.lvshou.magic.referral.entity.Referral;
import com.lvshou.magic.referral.service.ReferralService;
import com.lvshou.magic.referral.vo.ReferralVo;
import com.lvshou.magic.utils.MainUUID;

@Service
public class ReferralServiceImpl implements ReferralService {

	@Autowired
	ReferralDao referralDao;
	
	/*这段代码只能同时一个人执行*/
	@Override
	public List<ReferralVo> findAllParent(String userId) {
		// TODO Auto-generated method stub
		List list=new ArrayList<>();
		for(int i=0;i<2;i++) {
			if(userId==null) {return list;}
			List<Referral> referral=referralDao.findByChildId(userId);
			if(referral!=null&&referral.size()!=0) {
				ReferralVo vo=new ReferralVo();
				BeanUtils.copyProperties(referral.get(0), vo);
				vo.setClasses(i+1);//直接从第二级起步
				userId=vo.getUserId();
				list.add(vo);
			}
		}
		return list;
	}

	@Override
	public List<ReferralVo> findAllChild(String userId) {
		// TODO Auto-generated method stub
		List<ReferralVo> result=new ArrayList<>();
		int classes=1;
		deepSelect(userId, result, classes);
		return result;
	}
	/*递归查询*/
	public void deepSelect(String userId,List result,int classes) {
		List<Referral> list=referralDao.findByUserId(userId);
		System.out.print("userId="+userId);
		if(list==null||list.size()==0) {
			throw new CommonException(ResultEnum.CHILD_NONE);
		}
		Iterator<Referral> iterator=list.iterator();
		while(iterator.hasNext()) {
			if(classes>12) {
				return;
			}
			Referral referral=iterator.next();
			ReferralVo vo=new ReferralVo();
			BeanUtils.copyProperties(referral, vo);
			vo.setClasses(classes);
			result.add(vo);
			if(referral.getChildId()!=null) {
				classes=classes+1;
				deepSelect(referral.getChildId(), result, classes);
			}
		}
	}

	@Override
	public Referral insert(Referral referral) {
		// TODO Auto-generated method stub
		if(referral==null) {
			return null;
		}
		if(referral.getId()==null) {
			referral.setId(MainUUID.getUUID());
		}
		referralDao.save(referral);
		return referral;
	}

	@Override
	public Referral update(Referral referral) {
		// TODO Auto-generated method stub
		if(referral==null) {throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);}
		Referral referral2=findOne(referral.getId());
		if(referral2==null) {throw new CommonException(ResultEnum.UPDATE_NOT_FIND);}
		
		if(referral.getParentId()!=null) {
			referral2.setParentId(referral.getParentId());
		}
		if(referral.getChildId()!=null) {
			referral2.setChildId(referral.getChildId());
		}
		if(referral.getUserId()!=null) {
			referral2.setUserId(referral.getUserId());
		}
		referralDao.save(referral2);
		return referral2;
	}

	@Override
	public Page<Referral> findAll(int page,int size) {
		// TODO Auto-generated method stub
		return referralDao.findAll(PageRequest.of(page, size));
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id==null) {return;}
		
		try {
			referralDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CommonException(ResultEnum.ID_NOT_EXIT);
		}
	}

	/*找到一个推荐人不为空的用户*/
	@Override
	public Referral findParentOne(String userId) {
		// TODO Auto-generated method stub
		List<Referral> user=referralDao.findByUserId(userId);
		if(user==null||user.size()==0) {
			throw new CommonException(ResultEnum.USER_NOT_EXIT_REFERRAL);
		}
		Iterator<Referral> iterator=user.iterator();
		while(iterator.hasNext()) {
			Referral referral=iterator.next();
			if(referral.getParentId()!=null) {
				return referral;
			}
		}
		return user.get(0);
	}
	@Override
	public Referral findChildOne(String userId) {
		// TODO Auto-generated method stub
		
		return childDeep(userId);
	}
	
	private Referral childDeep(String userId) {
		List<Referral> user=referralDao.findByUserId(userId);
		if(user==null||user.size()==0) {
			throw new CommonException(ResultEnum.USER_NOT_EXIT_REFERRAL);
		}
		Iterator<Referral> iterator=user.iterator();
		while(iterator.hasNext()) {
			Referral referral=iterator.next();
			if(referral.getChildId()!=null) {
				return referral;
			}
		}
		for(int i=0;i<user.size();i++) {
			//查询子节点
			return childDeep(user.get(i).getChildId());
		}
		return null;
	}
	
	@Override
	public Referral findOne(String id) {
		// TODO Auto-generated method stub
		return referralDao.findById(id).orElse(null);
	}

	@Override
	public List<Referral> findByUserId(String userId){
		return referralDao.findByUserId(userId);
	}

	
	@Override
	public int findReferralAmount() {
		// TODO Auto-generated method stub
		return referralDao.findAll().size();
	}

	@Override
	public List<Referral> findByParentId(String parentId) {
		// TODO Auto-generated method stub
		return referralDao.findByParentId(parentId);
	}

	@Override
	public Referral findByChildId(String childId) {
		// TODO Auto-generated method stub
		return referralDao.findByChildId(childId).get(0);
	}
	
}
