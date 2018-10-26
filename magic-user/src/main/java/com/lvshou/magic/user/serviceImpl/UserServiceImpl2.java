package com.lvshou.magic.user.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.zxing.WriterException;
import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.service.UserCouponService;
import com.lvshou.magic.sys.entity.SysSetting;
import com.lvshou.magic.user.config.UserProperties;
import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.dao.UserDao;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.user.status.RetailStatus;
import com.lvshou.magic.user.status.VipStatus;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.utils.QrCodeUtils;

//@Service
public class UserServiceImpl2 implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MoneyService moneyService;
	@Autowired
	private UserProperties properties;
	@Autowired
	private UserCouponService userCouponService;
	@Override
	public Page<User> findAll(int page,int size) {
		// TODO Auto-generated method stub
		PageRequest pageable=PageRequest.of(page, size);
		return userDao.findAll(pageable);
	}

	
	@Override
	public List<User> findPageAllVips(int vip, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void allParent(User user, int classes, List<UserVo> list) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void judgePartner(User user) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<User> findNoIfy(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<User> findNoIfyNoPage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int amountNoIfy() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Transactional
	@Override
	public void insert(User user) {
		// TODO Auto-generated method stub
		String code=MainUUID.getCode();
		if(user!=null) {
			if(user.getId()==null||user.getId().equals("")) {
				user.setId(MainUUID.getUUID());
			}
			if(user.getReferralCode()==null||user.getReferralCode().equals("")) {
				List<User> users=userDao.findByReferralCode(code);
				while(users.size()>0) {
					code=MainUUID.getCode();
					users=userDao.findByReferralCode(code);
				}
				user.setReferralCode(code);
			}
			if(user.getVip()==null||user.getVip()==0) {
				user.setVip(1);
			}
			if(StringUtils.isEmpty(user.getQrcode())) {
				String name=MainUUID.getUUID();
				try {
					if(QrCodeUtils.createQrCode(properties.getPrefix_local_path()+name+".jpg", properties.getCode_path()+"?parent="+user.getReferralCode())) {
						user.setQrcode(name+".jpg");
					}
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(!StringUtils.isEmpty(user.getDirectPush())) {
			boolean have=false;
			List<UserVo> lists=new ArrayList<>();
			allParent(user, 3, lists);
			for (UserVo userVo : lists) {
				if(userVo.getReferralCode().equals(user.getDirectPush())) {
					user.setDirectPush(user.getDirectPush());
					have=true;
				}
			}
			if(!have) throw new CommonException("直推人的推荐码填写不正确或者是因为该直推人码并不是该用户的某个上级，请确认后添加");
		}
		if (!StringUtils.isEmpty(user.getParentCode())) {
			if(code.equals(user.getParentCode()))
				throw new CommonException("您不能将该用户设置为他自身的上一级");
			List<User> users=userDao.findByReferralCode(user.getParentCode());
			if (users.size()<=0) {
				throw new CommonException("上一级的推荐码填写错误，原因：并未找到推荐码为"+user.getParentCode()+"的用户");
			}
			prorealte(user, null);
		}
		//增加新用户的时候，应该同时向money表中添加数据
		Money money=new Money();
		moneyService.insert(money.setUserId(user.getId()));

		//user.setStatus(ReferrelStatus.OVER);
		//judgePartner(user);
		
		userDao.save(user);
		
	}
	
	private User childOne(User parent) {
		List<User> lists=userDao.findByParentCode(parent.getReferralCode());
		int l=lists.size()%5;
		if(l==0&&lists.size()>0) {
			for (User user : lists) {
				User user1=childOne(user);
				if(user1!=null)
					return user1;
			}
			return null;
		}else {
			return parent;
		}
	}
	private boolean prorealte(User user,String parentCode) {
		String code=null;
		if(parentCode!=null) code=parentCode;
		else code=user.getParentCode();
		List<User> p=userDao.findByReferralCode(code);
		if(p.size()<=0) throw new CommonException("该推荐码"+code+"并未对应一个用户，请仔细审核");
		User u=childOne(p.get(0));
		user.setParentCode(u.getReferralCode());
		return false;
	}
	private void allParents(User user,int classes,List<UserVo> list){
		if(classes<=0)
			return;
		if(StringUtils.isEmpty(user.getParentCode()))
			return ;
		List<User> lists=userDao.findByReferralCode(user.getParentCode());
		if(lists.size()<=0) throw new CommonException("没有找到该用户的上级推荐人,用户的编号为"+user.getNumId());
		list.add(UserConvert.covertToVo(lists.get(0), RetailStatus.classes-classes));
		if(StringUtils.isEmpty(lists.get(0).getParentCode()))
			return;
		int deep=classes-1;
		allParent(lists.get(0),deep, list);
	}
	@Override
	public User update(UserVo n) {
		return null;
	}

	@Override
	public User bindOldVip(UserVo n) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id!=null) {
			try {
				userDao.deleteById(id);
			} catch (Exception e) {
				// TODO: handle exception
				throw new CommonException(ResultEnum.ID_NOT_EXIT);
			}
			//moneyService.deleteByUserId(id);
		}
}

	@Override
	public Page<User> findByPhone(String phone, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findUsers(User user, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int allUsers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findAdd(Date date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findByPhone(String phone) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findByVip(int vip) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Coupon> getCoupons(String userId) {
		// TODO Auto-generated method stub
		User user=userDao.findById(userId).orElse(null);
		return new ArrayList<>(user.getCoupons());
	}

	@Override
	public String createShareCode(String prefixString, String userId, String background)
			throws WriterException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countName(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countVipName(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserVo> findAllChild(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> partners(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countPartners() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> directors(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countDirectors() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> findByDirectCode(String directCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> typeChilds(User user, int type) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public User findUser(User user) {
		// TODO Auto-generated method stub
				if(user==null) {
					return null;
				}
				if(user.getId()!=null&&!user.getId().equals(""))
					return userDao.findById(user.getId()).orElse(null);
				else if (user.getName()!=null) {
					List list=userDao.findByName(user.getName());
					if(list==null||list.size()<=0) {
						return null;
					}
					return (User) list.get(0);
				}else if (!StringUtils.isEmpty(user.getNumId())) {
					List<User> list=userDao.findByNumId(user.getNumId());
					if(list==null|list.size()<=0) {
						return null;
					}
					return list.get(0);
				}
				else if (user.getPhone()!=null&&!user.getPhone().equals("")) {
					List list=userDao.findByPhone(user.getPhone());
					if(list==null||list.size()<=0) {
						return null;
					}
					return (User) list.get(0);
				}else if (user.getReferralCode()!=null&&!user.getReferralCode().equals("")) {
					List list=userDao.findByReferralCode(user.getReferralCode());
					if(list==null||list.size()<=0) {
						return null;
					}
					return (User) list.get(0);
				}else if (user.getParentCode()!=null&&!user.getParentCode().equals("")) {
					List list=userDao.findByParentCode(user.getParentCode());
					if(list==null||list.size()<=0) {
						return null;
					}
					return (User) list.get(0);
				}else if (!StringUtils.isEmpty(user.getWid())) {
					User user2=userDao.findByWid(user.getWid());
					return user2;
				}
				return null;
	}


	@Override
	public void allChilds(User user, int deep, List<UserVo> lists) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}


	@Override
	public List<User> findAllByVip(int vip) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delBind(String userId) {
		// TODO Auto-generated method stub
		
	}

}
