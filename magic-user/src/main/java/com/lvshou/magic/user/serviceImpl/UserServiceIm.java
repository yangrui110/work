package com.lvshou.magic.user.serviceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.zxing.WriterException;
import com.lvshou.magic.entity.Coupon;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.entity.Reward;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.money.service.RewardService;
import com.lvshou.magic.service.UserCouponService;
import com.lvshou.magic.statics.Status;
import com.lvshou.magic.sys.entity.SysSetting;
import com.lvshou.magic.sys.service.SysSettingService;
import com.lvshou.magic.user.config.UserProperties;
import com.lvshou.magic.user.convert.StringConvert;
import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.dao.UserDao;
import com.lvshou.magic.user.dao.UserHistoryDao;
import com.lvshou.magic.user.entity.OldVip;
import com.lvshou.magic.user.entity.OldVipVo;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserHistory;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.OldVipService;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.user.status.ReferrelStatus;
import com.lvshou.magic.user.status.RetailStatus;
import com.lvshou.magic.user.status.VipStatus;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.utils.MoneyUtil;
import com.lvshou.magic.utils.QrCodeUtils;

@Service
public class UserServiceIm implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MoneyService moneyService;

	@Autowired
	private RewardService rewardService;
	
	@Autowired
	private UserProperties properties;

	@Autowired
	private UserCouponService userCouponService;
	
	@Autowired
	private OldVipService oldVipService;
	
	@Autowired
	private SysSettingService sysSettingService;
	@Autowired
	private UserHistoryDao userHistoryDao;
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
	public void insert(User user) throws Exception {
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
			if(user.getIfy()==null)
				user.setIfy(1);
			if(user.getStatus()==null)
				user.setStatus(1);
			if(user.getRsname()==null)
				user.setRsname(1);
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

		user.setStatus(ReferrelStatus.OVER);
		
			judgePartner(user);

		
		userDao.save(user);
		
	}
	
	private boolean prorealte(User user,String parentCode) throws Exception {
		String code=null;
		if(parentCode!=null) code=parentCode;
		else code=user.getParentCode();
		List<User> p=userDao.findByReferralCode(code);
		if(p.size()<=0) throw new CommonException("该推荐码"+code+"并未对应一个用户，请仔细审核");
		
		User u=null;
		/**执行childOne方法之前需要先判断用户的团队是否已经满了*/
		List<UserVo> ars=new ArrayList<>();
		allChilds(user, 3, ars);
		if(ars.size()%155==0&&ars.size()>0) {
			u=p.get(0);
		}else {
			u=childOne(p.get(0));
		}
		user.setParentCode(u.getReferralCode());
		return false;
	}

	@Override
	public void allChilds(User user,int deep,List<UserVo> lists) throws Exception{
		String string=StringConvert.parseUser(user);
		List<User> users=userDao.findAllUsersNoPage();
		//转换为List<String>
		List<String> results=StringConvert.convertListUser(users);
		//查找所有的下级用户
		List<String> rm=new ArrayList<>();
		StringConvert.allChilds(string, results, rm, deep);
		//转换为UserVo对象
		for (String string2 : rm) {
			lists.add((UserVo)StringConvert.toUserObject(string2, UserVo.class));
		}
	}
	private void allVipChilds(User user,int deep,List<UserVo> lists) throws Exception {
		String string=StringConvert.parseUser(user);
		List<User> users=userDao.findAllUsersNoPage();
		//转换为List<String>
		List<String> results=StringConvert.convertListUser(users);
		//查找所有的下级用户
		List<String> rm=new ArrayList<>();
		StringConvert.allChilds(string, results, rm, 3);
		//转换为UserVo对象
		for (String string2 : rm) {
			UserVo vo=(UserVo)StringConvert.toUserObject(string2, UserVo.class);
			if(vo.getVip().intValue()==VipStatus.VIP||vo.getVip().intValue()==VipStatus.DIRECTOR||vo.getVip().intValue()==VipStatus.PRE_PARTNER||vo.getVip().intValue()==VipStatus.PARTNER)
				lists.add(vo);
		}
	}
	/**这个方法调用之前需要先判断当前parent的团队是否是155的整数倍，如果是的话，就不应该执行这个方法*/
	private User childOne(User parent) throws Exception {
		List<UserVo> vos=new ArrayList<>();
		allChilds(parent, 3, vos);
		List<UserVo> deep0=new ArrayList<>();
		deep0.add(UserConvert.covertToVo(parent, 0));
		List<UserVo> deep1=new ArrayList<>();
		List<UserVo> deep2=new ArrayList<>();
		for (UserVo userVo : vos) {
			if(userVo.getDeep()==1)
				deep1.add(userVo);
			else if(userVo.getDeep()==2)
				deep2.add(userVo);
		}
		
		User resultUser=null;
		if((resultUser=full(deep0))!=null)
			return resultUser;
		else if((resultUser=full(deep1))!=null)
			return resultUser;
		else if((resultUser=full(deep2))!=null)
			return resultUser;
		else return null;
		/*List<User> lists=userDao.findByParentCode(parent.getReferralCode());
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
		}*/
	}
	
	private User full(List<UserVo> users) throws Exception {
		for (UserVo userVo : users) {
			List<UserVo> lists=new ArrayList<>();
			allChilds(UserConvert.covertToUser(userVo), 1, lists);
			int l=lists.size()%5;
			if(l==0&&lists.size()>0) 
				continue;
			else {
				return UserConvert.covertToUser(userVo);
			}
		}
		return null;
	}
	@Transactional
	@Override
	public User update(UserVo user) throws Exception {
		// TODO Auto-generated method stub
		if(user==null) {throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);}
		
		User user2=userDao.findById(user.getId()).orElse(null);
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
			if(user.getIfy()!=null)
				user2.setIfy(user.getIfy());
			if(user.getRsname()!=null)
				user2.setRsname(user.getRsname());
			if(user.getMail()!=null) {
				user2.setMail(user.getMail());
			}
			if(user.getBankName()!=null)
				user2.setBankName(user.getBankName());
			if(user.getBankCard()!=null)
				user2.setBankCard(user.getBankCard());
			if(user.getName()!=null) {
				user2.setName(user.getName());
			}
			if(user.getPhone()!=null) {
				user2.setPhone(user.getPhone());
			}
			if(user.getIcon()!=null) {
				user2.setIcon(user.getIcon());
			}
			if(user.getReferTime()!=null)
				user2.setReferTime(user.getReferTime());
			if(user.getProvince()!=null) {
				user2.setProvince(user.getProvince());
			}
			if(!StringUtils.isEmpty(user.getDirectPush())) {
				dealDirectPush(user, user2);
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
			if(!StringUtils.isEmpty(user.getWid()))
				user2.setWid(user.getWid());

			if(user.getVipTime()!=null) {
				user2.setVipTime(user.getVipTime());
			}
			//成为正式会员时，需要及时的返现
			if(user2.getStatus()!=null&&user2.getStatus()==ReferrelStatus.NO&&user.getVip()!=null&&user.getVip()==2) {
				SysSetting setting=sysSettingService.getOne();
				moneyService.addTotalIntegralById(setting.getVipMoney(), user2.getId());
				///////增加优惠卡券
				//userCouponService.addDefaults(user2.getId());
				upLevel(user2);
			}
			if(user.getVip()!=null&&user.getVip()==2) {
				user2.setStatus(ReferrelStatus.OVER); 
			}
			if(!StringUtils.isEmpty(user.getParentCode())) {
				if(StringUtils.isEmpty(user2.getParentCode())) {
					
					prorealte(user2,user.getParentCode());
				}else {
					if(!user.getParentCode().equalsIgnoreCase(user2.getParentCode()))
						prorealte(user2,user.getParentCode());
				}
			}
			userDao.save(user2);
			
			return user2;
		}
		return null;
	}
	
	private void dealDirectPush(UserVo ne,User old) {
		User sr=new User();
		BeanUtils.copyProperties(ne, sr);
		boolean have=false;
		if(ne.getDirectPush()!=null)
			have=true;
		if(have) {
			if(old.getDirectPush()!=null) {
				if(!old.getDirectPush().equals(ne.getDirectPush())) {
					List<UserVo> lists=new ArrayList<>();
					allParent(sr, 3, lists);
					boolean em=false;
					for (UserVo userVo : lists) {
						if(userVo.getReferralCode().equals(ne.getDirectPush()))
							{
								old.setDirectPush(ne.getDirectPush());
								em=true;
							}
							//throw new CommonException("直推人的推荐码"+ne.getDirectPush()+"不是该推荐人"+old.getName()+"的下一级,请重新确定输入合适的直推人");
					}
					if(!em) throw new CommonException("该直推人码"+ne.getDirectPush()+"不是该用户"+old.getName()+"的某个上级,请重新确定输入合适的直推人");
					
				}
			}else {
					List<UserVo> lists=new ArrayList<>();
					boolean em=false;
					allParent(sr, 3, lists);
					if(lists.size()<=0) throw new CommonException("用户"+old.getName()+"没有上级推荐人，该直推人的推荐码无效");
					for (UserVo userVo : lists) {
						if(userVo.getReferralCode().equals(ne.getDirectPush()))
							//throw new CommonException("直推人的推荐码"+ne.getDirectPush()+"不是该用户"+old.getName()+"的下一级,请重新确定输入合适的直推人");
							old.setDirectPush(ne.getDirectPush());
							em=true;
					}
					if(!em) throw new CommonException("该直推人码"+ne.getDirectPush()+"不是该用户"+old.getName()+"的某个上级,请重新确定输入合适的直推人");
				}
			}
	}

	private boolean upLevel(User user) {
		
		if(user.getVipTime()==null)
			user.setVipTime(new Date());
		SysSetting setting=sysSettingService.getOne();
		List<UserVo> users=new ArrayList<>();
		allParent(user, 3, users);
		BigDecimal total =new BigDecimal(0);
		for (UserVo userVo : users) {
			BigDecimal cur=MoneyUtil.calcMoney(userVo.getDeep(),setting.getBasic().intValue());
			total=total.add(cur.multiply(new BigDecimal(0.9)));
			moneyService.addTotalRewardById(cur.multiply(new BigDecimal(0.9)), userVo.getId());
			insertReward(user, userVo,cur.multiply(new BigDecimal(0.9)));
			//judgePartner(UserConvert.covertToUser(userVo));
			
			//都需要刷新user的推荐时间
			User ur=UserConvert.covertToUser(userVo);
			ur.setReferTime(new Date());
			userDao.save(ur);
		}
		total=setting.getBasic().subtract(total); 
		setting.setPlatForm(setting.getPlatForm().add(total));
		sysSettingService.update(setting);
		return false;
	}
	
	private void insertReward(User source,UserVo userVo,BigDecimal money) {
		Reward reward=new Reward();
		reward.setTotal(money);
		reward.setSource(source.getId());
		reward.setSourceName(source.getName());
		reward.setSourcePhone(source.getPhone());
		reward.setTarget(userVo.getId());
		reward.setTargetName(userVo.getName());
		reward.setTargetPhone(userVo.getPhone());
		rewardService.insert(reward);
	}
	private boolean judgeEachPart(User user) throws Exception {
		List<UserVo> lists=new ArrayList<>();
		allChilds(user, 1, lists);
		int count=0;
		for (UserVo userVo : lists) {
			List<UserVo> ars=new ArrayList<>();
			allChilds(UserConvert.covertToUser(userVo), 2, ars);
			for (UserVo userVo2 : ars) {
				if(userVo2.getVip()==VipStatus.PARTNER) {
					count++;
					break;
				}
			}
		}
		if(count>=5)
			return true;
		return false;
	}
	@Transactional
	@Override
	public void judgePartner(User user) throws Exception {
		if(user.getVip()==VipStatus.VIP||user.getVip()==VipStatus.SPECIAL) {
			List<UserVo> lists=new ArrayList<>();
			allVipChilds(user, 3, lists);
			if(lists.size()>=155) {
				user.setVip(VipStatus.PRE_PARTNER);
				user.setPrePartnerTime(new Date());
				UserHistory userHistory=new UserHistory();
				BeanUtils.copyProperties(user, userHistory);
				userHistory.setCreateTime(new Date());
				userHistory.setPrePartnerTime(user.getVipTime());
				userHistory.setId(MainUUID.getUUID());
				userHistoryDao.save(userHistory);
				userDao.save(user);
			}
		}else if (user.getVip()==VipStatus.PRE_PARTNER) {
			List<UserVo> lists=new ArrayList<>();
			allVipChilds(user, 3, lists);
			SysSetting setting=sysSettingService.getOne();
			if (isNextMonth(user.getPrePartnerTime())) {
				if(lists.size()>=setting.getReferNum()) {
					user.setVip(VipStatus.PARTNER);
					userDao.save(user);
				}
			}
		}else if (user.getVip()==VipStatus.PARTNER) {
			if(judgeEachPart(user)) {
				user.setVip(VipStatus.DIRECTOR);
				userDao.save(user);
			}
		}
	}
	private boolean isNextMonth(Date start) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(start);
		Calendar calendar2=Calendar.getInstance();
		calendar2.setTime(new Date());
		if(calendar.get(Calendar.MONTH)+1==calendar2.get(Calendar.MONTH))
			return true;
		return false;
	}
	@Override
	public void allParent(User user,int classes,List<UserVo> list){
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
	public Page<User> findByPhone(String phone, int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable=PageRequest.of(page, size);
		return userDao.findAllByPhone(phone, pageable);
	}
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
		int count=userDao.findByVipAmount(vip);
		if(vip==2) {
			int s1=userDao.findByVipAmount(VipStatus.SPECIAL);
			count=count+s1;
		}
		return count;
	}

	@Override
	public List<Coupon> getCoupons(String userId) {
		// TODO Auto-generated method stub
		User user=userDao.findById(userId).orElse(null);
		return new ArrayList<>(user.getCoupons());
	}

	public String createShareCode(String prefixString,String userId,String background) throws Exception {
		//Advertise advertise=advertiseService.findByType(2, 0, 10).get(0);
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
		
		//String background=advertise.getPics().substring(advertise.getPics().lastIndexOf("/"));
		QrCodeUtils.createQrBackground(prefixString+background, prefixString+user.getQrcode(),user.getName(),prefixString+ path);
		User basic=new User().setId(userId).setShareCode(path);
		UserVo vo=new UserVo();
		BeanUtils.copyProperties(basic, vo);
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
	
	/**每月最后一天，更新会员*/
	@Scheduled(cron="0 1 1 1 * ?")
	public void timeDistance() {
		Date date2=new Date();
		List<User> users=userDao.findAll();
		for (User user : users) {
			if(user.getVip()==VipStatus.VIP||user.getVip()==VipStatus.PARTNER||user.getVip()==VipStatus.DIRECTOR) {
				user.setVip(VipStatus.SPECIAL);
				userDao.save(user);
			}else if(user.getVip()==VipStatus.PRE_PARTNER){
				Date date=user.getPrePartnerTime();
				
				Calendar calendar1=Calendar.getInstance();
				calendar1.setTime(date);
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(date2);
				if((calendar.get(Calendar.MONTH)-calendar1.get(Calendar.MONTH))==2) {
					user.setVip(VipStatus.SPECIAL);
					userDao.save(user);
				}
			}
		}
	}

	@Scheduled(cron="0 30 23 * * ?")
	public void updateDied() {
		Date date2=new Date();
		List<User> users=userDao.findAll();
		for (User user : users) {
			Date date=user.getReferTime()==null?user.getVipTime():user.getReferTime();
			long dis=date2.getTime()-date.getTime();
			int day=(int) (dis/(1000*60*60*24));
			if(day>=90) {
				user.setVip(VipStatus.DIED);
				userDao.save(user);
			}
		}
	}
	
	@Override
	public List<User> partners(int page,int size){
		return userDao.pagePartners(VipStatus.PARTNER,PageRequest.of(page, size));
	}

	@Override
	public int countPartners() {
		// TODO Auto-generated method stub
		return userDao.countPartners(VipStatus.PARTNER);
	}

	@Override
	public List<User> directors(int page, int size) {
		// TODO Auto-generated method stub
		return userDao.pageDirectors(VipStatus.DIRECTOR, PageRequest.of(page, size));
	}

	@Override
	public int countDirectors() {
		// TODO Auto-generated method stub
		return userDao.countDirectors(VipStatus.DIRECTOR);
	}

	@Override
	public List<User> findByDirectCode(String directCode) {
		// TODO Auto-generated method stub
		return userDao.findByDirectPush(directCode);
	}

	@Override
	public List<User> typeChilds(User user, int type) throws Exception {
		// TODO Auto-generated method stub
		List<UserVo> aList=new ArrayList<>();
		allChilds(user, 3, aList);
		List<User> result=new ArrayList<>();
		
		for (UserVo userVo : aList) {
			if(type==0)
				result.add(UserConvert.covertToUser(userVo));
			else if(userVo.getDeep()==type)
				result.add(UserConvert.covertToUser(userVo));
		}
		return result;
	}
	@Override
	public User bindOldVip(UserVo n) {
		if(!StringUtils.isEmpty(n.getPhone())&&!StringUtils.isEmpty(n.getOwnReferCode())) {
			List<User> users=userDao.findByNameAndPhoneAndReferralCode(n.getName(),n.getOwnReferCode());
			
			if(users.size()<=0) throw new CommonException("用户的手机号或者用户名或者推荐码填写不正确，请重新填写");
			if(StringUtils.isEmpty(users.get(0).getWid())) {
				users.get(0).setPhone(n.getPhone());
				users.get(0).setWid(n.getWid());
				users.get(0).setIfy(2);
				userDao.save(users.get(0));
			}
			return users.get(0);
		}
		return null;
	}

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		return userDao.findAllUsersNoPage();
	}

	@Override
	public List<User> findAllByVip(int vip) {
		// TODO Auto-generated method stub
		if(vip==0)
			return userDao.findAllUsersNoPage();
		List<User> users=userDao.findAllByVips(vip);
		if(vip==2) {
			List<User> copy=userDao.findAllByVips(VipStatus.SPECIAL);
			for (User user : copy) {
				users.add(user);
			}
		}
		return users;
	}

	@Override
	public List<User> findPageAllVips(int vip, int page, int size) {
		// TODO Auto-generated method stub
		return userDao.findAllByVips(vip, PageRequest.of(page-1, size));
	}

	@Override
	public List<User> findNoIfy(int page, int size) {
		// TODO Auto-generated method stub
		return userDao.allNoIfy(PageRequest.of(page-1, size));
	}

	@Override
	public List<User> findNoIfyNoPage() {
		// TODO Auto-generated method stub
		return userDao.allNoIfyNoPage();
	}

	@Override
	public int amountNoIfy() {
		// TODO Auto-generated method stub
		return userDao.amountNoIfy();
	}

	@Override
	public void delBind(String userId) {
		// TODO Auto-generated method stub
		User user=userDao.findById(userId).orElse(null);
		if(user==null) throw new CommonException("解绑用户不存在");
		user.setWid("");
		user.setIfy(1);
		userDao.save(user);
	}
	
}
