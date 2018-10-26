package com.lvshou.magic.user.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.zxing.WriterException;
import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.money.service.RewardService;
import com.lvshou.magic.service.UserCouponService;
import com.lvshou.magic.sys.entity.SysSetting;
import com.lvshou.magic.sys.service.SysSettingService;
import com.lvshou.magic.user.config.UserProperties;
import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.dao.UserDao;
import com.lvshou.magic.user.entity.OldVip;
import com.lvshou.magic.user.entity.OldVipVo;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.OldVipService;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.user.status.ReferrelStatus;
import com.lvshou.magic.user.status.VipStatus;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.utils.MoneyUtil;
import com.lvshou.magic.utils.QrCodeUtils;

//@Service
public class UserServiceImpl implements UserService{

	@Override
	public void allParent(User user, int classes, List<UserVo> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void judgePartner(User user) {
		// TODO Auto-generated method stub
		
	}

	@Autowired
	UserDao userDao;
	
	@Autowired
	MoneyService moneyService;

	@Autowired
	RewardService rewardService;
	
	@Autowired
	UserProperties properties;

	@Autowired
	UserCouponService userCouponService;
	
	@Autowired
	private OldVipService oldVipService;
	
	@Autowired
	private SysSettingService sysSettingService;
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
			User list=userDao.findByWid(user.getWid());
			return list;
		}
		return null;
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

	@Override
	public List<User> findPageAllVips(int vip, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findAll(int page,int size) {
		// TODO Auto-generated method stub
		PageRequest pageable=PageRequest.of(page, size);
		return userDao.findAll(pageable);
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
		
		//增加新用户的时候，应该同时向money表中添加数据
		Money money=new Money();
		moneyService.insert(money.setUserId(user.getId()));

		
		userDao.save(user);
		
	}

	@Transactional
	@Override
	public User update(UserVo user) {
		// TODO Auto-generated method stub
		if(user==null) {throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);}
		User basic=new User();
		BeanUtils.copyProperties(user, basic);
		User user2=findUser(basic);
		if(user2!=null) {
			
			if(user.getAddress()!=null) {
				user2.setAddress(user.getAddress());
			}
			if(user.getCity()!=null) {
				user2.setCity(user.getCity());
			}
			if(user.getIdentity()!=null) {
				user2.setIdentity(user.getIdentity());
			}
			if(user.getShareCode()!=null) {
				user2.setShareCode(user.getShareCode());
			}
			if(user.getMail()!=null) {
				user2.setMail(user.getMail());
			}
			if(user.getName()!=null) {
				user2.setName(user.getName());
			}
			if(user.getPhone()!=null) {
				user2.setPhone(user.getPhone());
			}
			if(user.getIcon()!=null) {
				user2.setIcon(user.getIcon());
			}
			if(user.getProvince()!=null) {
				user2.setProvince(user.getProvince());
			}
			if(user.getReferralCode()!=null) {
				user2.setReferralCode(user.getReferralCode());
			}
			if(user.getVip()!=null) {
				user2.setVip(user.getVip());
			}
			if(user.getCountry()!=null) {
				user2.setCountry(user.getCountry());
			}
			/*if(!StringUtils.isEmpty(user.getParentCode())) {
				if(StringUtils.isEmpty(user.getParentCode())||!user.getParentCode().equals(user2.getParentCode()))
					if(!oldVipService.hasParent(user2.getName(), user.getPhone())) {
						User parent=disParentCode(user.getParentCode(),user2.getId());
						User parentChild=findChildOne(parent, 3);
						if(StringUtils.isEmpty(parentChild.getReferralCode())) {
							user2.setReferName(parentChild.getName());
							user2.setReferArea(parentChild.getProvince());
						}else
							user2.setParentCode(parentChild.getReferralCode());
					}
			}*/
			if(!StringUtils.isEmpty(user.getParentCode())) {
				disOldVip(basic,user2);
			}
			inOld(basic, user2);
			//成为正式会员时，需要及时的返现
			if(user2.getStatus()==ReferrelStatus.NO&&user.getVip()!=null&&user.getVip()==2) {
				Money moneyx=moneyService.findOne(new Money().setUserId(user.getId()));
				SysSetting sys=sysSettingService.getOne();
				moneyx.setTotalIntegral(MoneyUtil.calcMoney(0, sys.getBasic().intValue()));
				moneyService.update(moneyx);
				///////增加优惠卡券
				userCouponService.addDefaults(user2.getId());
				//upgrade(user2.getReferralCode(),user.getParentCode());
				upLevel(user2);
			}
			if(user.getVip()!=null&&user.getVip()==2) {
				user2.setStatus(ReferrelStatus.OVER); 
			}
			if(!StringUtils.isEmpty(user.getParentCode())) {
				User parent=disParentCode(user.getParentCode(), user.getId());
				user2.setParentCode(parent.getReferralCode());
			}
			userDao.save(user2);
			
			return user2;
		}
		return null;
	}

	/**判断传入推荐码的合法性*/
	private User disParentCode(String parentCode,String userId) {
		List<User> list=userDao.findByReferralCode(parentCode);
		if(list.size()<=0)
			throw new CommonException("待绑定的推荐码不存在");
		if(list.get(0).getId().equals(userId))
			throw new CommonException("您不能绑定自身的推荐码");
		
		return list.get(0);
	}
	
	/**
	 * @param user 传入的是父类user
	 * @param classes 查找深度
	 * */
	private User findChildOne(User user,int classes) {
		if (classes==0) {
			return null;
		}
		//首先需要判断该用户是否存在于老系统中
		if(oldVipService.exist(user.getName(), user.getPhone())) {
			List<OldVip> oldVips=oldVipService.findByNameAndPhone(user.getName(), user.getPhone());
			OldVip oldVip=oldVipService.findChildOne(oldVips.get(0), classes);
			if(oldVip==null)
				return user;
			else {
				User user2=new User();
				user2.setName(oldVip.getName()).setProvince(oldVip.getPosition());
				return user2;
			}
		}else {
			List<User> users=userDao.findByParentCode(user.getReferralCode());
			if (users.size()<5)
				return user;
			else if (users.size()==5) {
				for (User user2 : users) {
					return findChildOne(user2, classes--);
				}
			}else {
				throw new CommonException("新系统中用户编号为"+user.getNumId()+"的下一级已经超过了5个用户，请仔细审查");
			}
		}
		return null;	
	}
	
	/**
	 * 更新的时候判断是否需要查询oldVip
	 * @param old 更新时传进来的user
	 * @param ne user里面的oldVip对象
	 * */
	private void disOldVip(User old,User ne) {
		if(ne.getParentCode()!=null)
			return;
		List<User> users=userDao.findByReferralCode(old.getParentCode());
		if(users.size()<=0) throw new CommonException("用户"+old.getNumId()+"的上一级推荐人:"+old.getParentCode()+"不存在于系统中");
		if(StringUtils.isEmpty(ne.getReferName())&&StringUtils.isEmpty(ne.getReferArea()))
			if((!StringUtils.isEmpty(users.get(0).getName())&&(!StringUtils.isEmpty(users.get(0).getPhone())))) {
				List<OldVip> lists=oldVipService.findByNameAndPhone(users.get(0).getName(), users.get(0).getPhone());
				if(lists.size()>1)
					throw new CommonException("绑定的用户名和手机号存在多个账号于系统中，请联系管理员~~");
				else if (lists.size()<=0) {
					return;
				}else {
					ne.setReferArea(users.get(0).getProvince());
					ne.setReferName(users.get(0).getName());
				}
			}
	}
	
	@Override
	public Page<User> findByPhone(String phone, int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable=PageRequest.of(page, size);
		return userDao.findAllByPhone(phone, pageable);
	}

	@Override
	public User bindOldVip(UserVo n) {
		// TODO Auto-generated method stub
		return null;
	}

	/**查找所有的父节点*/
	private void findAllParent(User user,int classes,List<UserVo> lists) {
		if(classes>3)
			return;
		if(!StringUtils.isEmpty(user.getParentCode())) {
			List<User> ss=userDao.findByReferralCode(user.getParentCode());
			if(ss.size()<=0) throw new CommonException("用户编号为:"+user.getNumId()+"的上一级并不存在系统中");
			lists.add(UserConvert.covertToVo(ss.get(0),classes));
			classes=classes+1;
			findAllParent(ss.get(0), classes, lists);
		}else if (!StringUtils.isEmpty(user.getReferName())&&!StringUtils.isEmpty(user.getReferArea())) {
			OldVip oldVip=new OldVip();
			oldVip.setName(user.getReferName());
			oldVip.setPosition(user.getReferArea());
			List<OldVipVo> oldVips=new ArrayList<>();
			int num=classes=classes+1;
			oldVipService.findAllParent(oldVip, num, oldVips);
			//将oldvips转换为user
			User user2=null;
			for (OldVipVo oldVip2 : oldVips) {
				user2=new User();
				List<User> p=userDao.findByNameAndProvince(oldVip2.getName(), oldVip2.getPosition());
				if(p.size()<=0) {
					user2.setId(oldVip2.getId());
					user2.setName(oldVip2.getName());
					user2.setProvince(oldVip2.getPosition());
					lists.add(UserConvert.covertToVo(user2, oldVip2.getDeep()));
				}else lists.add(UserConvert.covertToVo(p.get(0),classes));
			}
		}
	}
	
	public boolean inOld(User user,User old) {
		if(!StringUtils.isEmpty(user.getName())&&!StringUtils.isEmpty(user.getPhone()))
			if(StringUtils.isEmpty(user.getReferName())&&StringUtils.isEmpty(user.getReferArea())) {
				List<OldVip> list=oldVipService.findByNameAndPhone(user.getName(), user.getPhone());
				if(list.size()<=0) return false;
				//接下来就是在老会员系统中，进行属性的粘贴复制
				old.setReferName(list.get(0).getReferName());
				old.setReferArea(list.get(0).getReferArea());
				old.setVip(VipStatus.VIP);
				old.setStatus(ReferrelStatus.OVER);
				return true;
			}
		return false;
	}
	@Transactional
	private boolean upLevel(User user) {
		List<UserVo> lists=new ArrayList<>();
		findAllParent(user, 1, lists);
		SysSetting setting=sysSettingService.getOne();
		for (UserVo parent : lists) {
			if(StringUtils.isEmpty(parent.getNumId())) {
				oldVipService.addTotalRewardById(parent.getId(), MoneyUtil.calcMoney(parent.getDeep(), setting.getBasic().intValue()));
			}else {
				moneyService.addTotalRewardById(MoneyUtil.calcMoney(parent.getDeep(),setting.getBasic().intValue()), parent.getId());
			}
		}
		return false;
	}
	
	/*@Transactional
	@Override
	public boolean upgrade(String referralCode,String parentCode) {
		// TODO Auto-generated method stub
		//根据推荐码查找用户，然后查找出所有的parent，给每个parent相应的推荐金额
		if(StringUtils.isEmpty(parentCode)) {
			return false;
		}
		BigDecimal money1=null;
		User user1=new User();
		user1.setReferralCode(parentCode);
		User user=findUser(user1);
		if(user==null) {
			//推荐码不存在异常
			throw new CommonException(ResultEnum.REFERRAL_CODE_NOT_EXIT);
		}
		//查找referral表，需要查找到一个子类不为空的推荐人
		Referral referral=referralService.findChildOne(user.getId());
		user.setId(referral.getUserId());
		money1=MoneyUtil.calcMoney(1);
		moneyService.addTotalRewardById(money1, user.getId());
		//moneyService.addTotalIntegralById(money1, user.getId());
		System.out.println("第一级推荐者充值成功");
		Reward reward1=insert(referral);
		reward1.setTotal(money1);
		rewardService.insert(reward1);
		List<ReferralVo> list=referralService.findAllParent(user.getId());
		System.out.println("list.size="+list.size());
		if(list!=null&&list.size()!=0) {
			Iterator<ReferralVo> iterator=list.iterator();
			while(iterator.hasNext()) {
				ReferralVo vo=iterator.next();
				查询到所有父用户之后,更新金额表
				 * 即每个用户的金额都会相应的增加
				 * 
				Reward reward=insert(vo);
				money1=MoneyUtil.calcMoney(vo.getClasses()+1);
				reward.setTotal(money1);
				money1=MoneyUtil.calcMoney(vo.getClasses()+1);
				//moneyService.addMoneyById(money1, vo.getUserId());
				//增加个人积
				//增加奖励金总额
				//moneyService.addTotalIntegralById(money1, vo.getUserId());
				moneyService.addTotalRewardById(money1, vo.getUserId());
				rewardService.insert(reward);
			}
		}
		return true;
	}

	private Reward insert(ReferralVo referral) {
		//记录表中添加数据
		Reward reward=new Reward();
		reward.setSource(referral.getChildId());
		reward.setTarget(referral.getUserId());
		User temp=userDao.findById(referral.getChildId()).orElse(null);
		reward.setSourcePhone(temp.getPhone());
		reward.setSourceName(temp.getName());
		temp=userDao.findById(referral.getUserId()).orElse(null);
		reward.setTargetPhone(temp.getPhone());
		reward.setTargetName(temp.getName());
		
		//插入完毕
		return reward;
	}
	private Reward insert(User source,UserVo target) {
		//记录表中添加数据
		Reward reward=new Reward();
		reward.setSource(referral.getChildId());
		reward.setTarget(referral.getUserId());
		User temp=userDao.findById(referral.getChildId()).orElse(null);
		reward.setSourcePhone(temp.getPhone());
		reward.setSourceName(temp.getName());
		temp=userDao.findById(referral.getUserId()).orElse(null);
		reward.setTargetPhone(temp.getPhone());
		reward.setTargetName(temp.getName());
		
		//插入完毕
		return reward;
	}*/
	@Override
	public Page<User> findUsers(User user,int page,int size) {
		// TODO Auto-generated method stub
		if(user==null) {
			return null;
		}
		if (user.getVip()!=null&&user.getVip()!=0&&user.getVip()!=1) {
			if(!StringUtils.isEmpty(user.getName())) {
				return userDao.findVipName(user.getName(), VipStatus.VIP, PageRequest.of(page, size));
			}else if (!StringUtils.isEmpty(user.getPhone())) {
				return userDao.findAllByphoneAndVip(user.getPhone(), VipStatus.VIP, PageRequest.of(page, size));
			}else if (!StringUtils.isEmpty(user.getNumId())) {
				return userDao.findAllByNumIdAndVip(user.getNumId(), VipStatus.VIP, PageRequest.of(page, size));
			}else if (!StringUtils.isEmpty(user.getReferralCode())) {
				return userDao.findAllByReferralCodeAndVip(user.getReferralCode(), VipStatus.VIP, PageRequest.of(page, size));
			}
			return userDao.findAllByVip(user.getVip(),PageRequest.of(page, size));
		}else if(user.getName()!=null&&!user.getName().equals("")) {
			return userDao.findName(user.getName(), PageRequest.of(page, size));
		}
		else if(user.getPhone()!=null&&!user.getPhone().equals("")) {
			return userDao.findAllByPhone(user.getPhone(), PageRequest.of(page, size));
		}else if (!StringUtils.isEmpty(user.getNumId())) {
			return userDao.findAllByNumId(user.getNumId(),PageRequest.of(page, size));
		}else if (!StringUtils.isEmpty(user.getReferralCode())) {
			return userDao.findAllByReferralCode(user.getReferralCode(),PageRequest.of(page, size));
		}
	return null;
	}

	@Override
	public int allUsers() {
		// TODO Auto-generated method stub
		return userDao.findAllUsers();
	}

	@Override
	public int findAdd(Date date) {
		// TODO Auto-generated method stub
		
		return userDao.findTimes(date).size();
	}

	@Override
	public int findByPhone(String phone) {
		// TODO Auto-generated method stub
		return userDao.findByPhone(phone).size();
	}
	@Override
	public int findByVip(int vip) {
		return userDao.findByVipAmount(vip);
	}

	@Override
	public List<Coupon> getCoupons(String userId) {
		// TODO Auto-generated method stub
		User user=userDao.findById(userId).orElse(null);
		return new ArrayList<>(user.getCoupons());
	}

	public String createShareCode(String prefixString,String userId,String background) throws WriterException, IOException {
		//Advertise advertise=advertiseService.findByType(2, 0, 10).get(0);
		System.out.println("userId="+userId);
		User user=findUser(new User().setId(userId));
		if(user==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		String path=null;
		if(user.getShareCode()==null) {
			path=MainUUID.getUUID()+".jpg";
		}else {
			path=user.getShareCode();
		}
		QrCodeUtils.createQrBackground(prefixString+background, prefixString+user.getQrcode(),user.getName(),prefixString+ path);
		User ser=new User().setId(userId).setShareCode(path);
		UserVo vo=new UserVo();
		BeanUtils.copyProperties(ser, vo);
		update(vo);
		return path;
	}

	@Override
	public int countName(String name) {
		// TODO Auto-generated method stub
		
		return userDao.countsName(name);
	}

	@Override
	public int countVipName(String name) {
		// TODO Auto-generated method stub
		return userDao.countVipName(name, VipStatus.VIP);
	}

	@Override
	public List<UserVo> findAllChild(String id) {
		// TODO Auto-generated method stub
		User user=userDao.findById(id).orElse(null);
		List<UserVo> vos=new LinkedList<UserVo>();
		child(user.getReferralCode(), vos, 1);
		return vos;
	}
	//向下梯度查找法
	public void child(String parentCode,List<UserVo> lists,int num) {
		if(num>12) {
			return;
		}
		List<User> vos=userDao.findByParentCode(parentCode);
		Iterator<User> iterator=vos.iterator();
		UserVo vo=null;
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			vo=new UserVo();
			BeanUtils.copyProperties(user, vo);
			vo.setDeep(num);
			lists.add(vo);
			child(user.getReferralCode(), lists, num+1);
		}
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
