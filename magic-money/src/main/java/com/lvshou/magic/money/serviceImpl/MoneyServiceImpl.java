package com.lvshou.magic.money.serviceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.dao.MoneyDao;
import com.lvshou.magic.money.dao.RewardDao;
import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.operation.entity.Operation;
import com.lvshou.magic.operation.service.OperationService;
import com.lvshou.magic.operation.statics.Constant;
import com.lvshou.magic.recharge.entity.Recharge;
import com.lvshou.magic.recharge.service.RechargeService;
import com.lvshou.magic.utils.MainUUID;

@Service
public class MoneyServiceImpl implements MoneyService{

	@Autowired
	private MoneyDao moneyDao;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private RechargeService rechargeService;
	
	@Autowired
	private RewardDao rewardDao;
	
	@Override
	public List<Money> findAll(int page, int size) {
		// TODO Auto-generated method stub
		List lists=moneyDao.findAllMoneys(PageRequest.of(page, size));
		
		return parseNoMonthReward(lists);
	}
	
	/**分页查找数据
	 * @throws ParseException */
	@Override
	public List<Money> findMoneyWithMonthRewardPage(int page,int size,String date) throws ParseException{
		int year=0;
		int month=0;
		if(!StringUtils.isEmpty(date)) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
			java.util.Date date2=dateFormat.parse(date);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date2);
			year=calendar.get(Calendar.YEAR);
			month=calendar.get(Calendar.MONTH)+1;
		}
		if(year!=0&&month!=0) {
			return findMoneyWithMonthRewardHavePage(page,size,year,month);
		}else {
			return findAll(page, size);
		}
	}
	
	@Override
	public List<Money> findAllNoMonthRewardNoPage(){
		List lists=moneyDao.findAllMoneys();
		return parseNoMonthReward(lists);
	}
	
	public List<Money> findMoneyWithMonthRewardHavePage(int page,int size,int year,int month){
		List daos=rewardDao.countReward(year, month,PageRequest.of(page-1, size));
		return parseHaveMonthReward(daos);
	}
	
	/**这个主要是用来导出数据时数据使用*/
	@Override
	public List<Money> findMoneyWithMonthReward(int year,int month){
		List daos=rewardDao.countReward(year, month);
		return parseHaveMonthReward(daos);
	}
	/**转换没有月奖励金的数据*/
	private List<Money> parseNoMonthReward(List lists){
		List<Money> list=new ArrayList<>();
		Money money=null;
		try {
		for (Object object : lists) {
			Object[] objects=(Object[])object;
			money=new Money();
			money.setId(parseString(objects[0]));
			money.setUserId(parseString(objects[1]));
			money.setBalance(parseDecimal(objects[2]));
			money.setIntegral(parseDecimal(objects[3]));
			money.setTotalIntegral(parseDecimal(objects[4]));
			money.setReward(parseDecimal(objects[5]));
			money.setTotalReward(parseDecimal(objects[6]));
			money.setCreateTime(parseDate(objects[7]));
			money.setUpdateTime(parseDate(objects[8]));
			money.setNumId(parseString(objects[9]));
			money.setName(parseString(objects[10]));
			money.setPhone(parseString(objects[11]));
			money.setMonthReward(new BigDecimal(0));
			list.add(money);
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	private List<Money> parseHaveMonthReward(List daos){
		List<Money> moneys=new ArrayList<>();
		try {
		Money money=null;
		for (Object object : daos) {
			money=new Money();
			Object[] rs=(Object[])object;
			money.setMonthReward(parseDecimal(rs[0]));
			money.setId(parseString(rs[1]));
			money.setUserId(parseString(rs[2]));
			money.setBalance(parseDecimal(rs[3]));
			money.setIntegral(parseDecimal(rs[4]));
			money.setTotalIntegral(parseDecimal(rs[5]));
			money.setReward(parseDecimal(rs[6]));
			money.setTotalReward(parseDecimal(rs[7]));
			money.setCreateTime(parseDate(rs[8]));
			money.setUpdateTime(parseDate(rs[9]));
			money.setNumId(parseString(rs[10]));
			money.setName(parseString(rs[11]));
			money.setPhone(parseString(rs[12]));
			moneys.add(money);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return moneys;
	}
	
	private String parseString(Object object) {
		if(object==null) return null;
		else return (String)object;
	}
	
	private BigDecimal parseDecimal(Object object) {
		if(object==null) return new BigDecimal(0);
		else {
			if(object instanceof Integer) return new BigDecimal((Integer)object);
			else if(object instanceof BigDecimal) return (BigDecimal)object;
			else if(object instanceof String) return new BigDecimal((String)object);
			else return new BigDecimal(0);
		}
	}
	private java.sql.Timestamp parseDate(Object date) throws Exception {
		if(date==null) return null;
		else {
			java.sql.Timestamp date2 =(java.sql.Timestamp)date;
			return date2;
		} 
	}

	@Override
	public Money findOne(Money money) {
		// TODO Auto-generated method stub
		if(money==null) {
			return null; 
		}
		if(money.getId()!=null) {
			return moneyDao.findById(money.getId()).orElse(null);
		}else if (money.getUserId()!=null) {
			List list=moneyDao.findUser(money.getUserId());
			if(list!=null&&list.size()>0) {
				return (Money) list.get(0);
			}
		}
		return null;
	}

	
	@Override
	public List<Money> findAllNoPage() {
		// TODO Auto-generated method stub
		return moneyDao.moneyNoPage();
	}


	@Override
	public Money update(Money money) {
		// TODO Auto-generated method stub
		if(money==null) {throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);}
		Money money2=findOne(money);
		if(money2==null) {throw new CommonException(ResultEnum.UPDATE_NOT_FIND);}
		
		if(money.getBalance()!=null) {
			money2.setBalance(money.getBalance());
		}
		if(money.getIntegral()!=null) {
			money2.setIntegral(money.getIntegral());
		}
		if(money.getReward()!=null) {
			money2.setReward(money.getReward());
		}
		if(money.getTotalIntegral()!=null) {
			money2.setTotalIntegral(money.getTotalIntegral());
		}
		if(money.getTotalReward()!=null) {
			money2.setTotalReward(money.getTotalReward());
		}
		if(!StringUtils.isEmpty(money.getUserId())) {
			money2.setUserId(money.getUserId());
		}
		moneyDao.save(money2);
		return money2;
	}

	@Override
	public Money insert(Money money) {
		// TODO Auto-generated method stub
		if(money!=null) {
			if(money.getId()==null) {
				money.setId(MainUUID.getUUID());
			}
			if(money.getBalance()==null) {
				money.setBalance(new BigDecimal(0));
			}
			if(money.getIntegral()==null) {
				money.setIntegral(new BigDecimal(0));
			}
			if(money.getReward()==null) {
				money.setReward(new BigDecimal(0));
			}
			if(money.getTotalIntegral()==null) {
				money.setTotalIntegral(new BigDecimal(0));
			}
			if(money.getTotalReward()==null) {
				money.setTotalReward(new BigDecimal(0));
			}
		}
		moneyDao.save(money);
		return money;
	}


	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		try {
			moneyDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CommonException(ResultEnum.ID_NOT_EXIT);
		}
	}


	@Override
	@Transactional
	public void addMoneyById(BigDecimal money, String userId) {
		// TODO Auto-generated method stub
		Money money2=findOne(new Money().setUserId(userId));
		if(money2==null) {throw new CommonException(ResultEnum.NOT_FIND_MONEY_TABLE);}
		BigDecimal amount=money2.getBalance();
		money2.setBalance(amount.add(money));
		moneyDao.save(money2);
		//向operation中插入数据
		Recharge recharge=new Recharge();
		recharge.setMoney(money);
		recharge.setUserId(userId);
		recharge.setOperator("self");
		rechargeService.insert(recharge);
	}

	@Override
	@Transactional
	public void delMoneyById(BigDecimal money, String userId,int op) {
		//op取1是消费，0是提现
		// TODO Auto-generated method stub
		Money money2=findOne(new Money().setUserId(userId));
		if(money2==null) {throw new CommonException(ResultEnum.NOT_FIND_MONEY_TABLE);}
		BigDecimal amount=money2.getBalance();
		if(amount.compareTo(money)==-1) {throw new CommonException(ResultEnum.CASH_OVER_CONSUME);}
		
		money2.setBalance(amount.subtract(money));
		Operation operation=new Operation();
		operation.setMoney(money);
		operation.setUserId(userId);
		if(op==1) {
			money2.setTotalIntegral(money2.getTotalIntegral().add(amount));
			operation.setOperate(Constant.CONSUME);
		}else if (op==0) {
			operation.setOperate(Constant.WITHDRAW);
		}else {
			operation.setOperate("");
		}
		moneyDao.save(money2);
		operationService.insert(operation);
	}
	public void delIntegralById(BigDecimal money, String userId) {
		// TODO Auto-generated method stub
		Money money2=findOne(new Money().setUserId(userId));
		if(money2==null) {throw new CommonException(ResultEnum.NOT_FIND_MONEY_TABLE);}
		BigDecimal amount=money2.getIntegral();
		if(amount.compareTo(money)==-1) {throw new CommonException(ResultEnum.CASH_OVER_CONSUME);}
		
		money2.setIntegral(amount.subtract(money));
		
		moneyDao.save(money2);
		
	}
	
	@Override
	public boolean payimpl(BigDecimal money,String userId, String mode) {
		// TODO Auto-generated method stub
		if(mode.equals("balance")) {
			delMoneyById(money, userId, 1);
		}else if (mode.equals("integral")) {
			delIntegralById(money, userId);
		}else if (mode.equals("wechat")) {
			
		}
		return true;
	}

	@Override
	public int findMoneyAmount() {
		// TODO Auto-generated method stub
		return moneyDao.findMoneyAmount();
	}


	@Override
	public void addTotalRewardById(BigDecimal money, String userId) {
		// TODO Auto-generated method stub
		Money money2=findOne(new Money().setUserId(userId));
		if(money2==null) {throw new CommonException(ResultEnum.NOT_FIND_MONEY_TABLE);}
		BigDecimal amount=money2.getTotalReward();
		money2.setTotalReward(amount.add(money));
		moneyDao.save(money2);
	}


	@Override
	@Transactional
	public void delTotalRewardById(BigDecimal money, String userId) {
		// TODO Auto-generated method stub
		Money money2=findOne(new Money().setUserId(userId));
		if(money2==null) {throw new CommonException(ResultEnum.NOT_FIND_MONEY_TABLE);}
		BigDecimal amount=money2.getTotalReward();
		if(amount.compareTo(money)==-1) {throw new CommonException(ResultEnum.CASH_OVER_CONSUME);}
		
		money2.setTotalReward(amount.subtract(money));
		
		moneyDao.save(money2);
		Operation operation=new Operation();
		operation.setMoney(money);
		operation.setStatus(0);
		operation.setOperate(Constant.WITHDRAW);
		operation.setUserId(userId);
		operationService.insert(operation);
	}


	@Override
	public void addTotalIntegralById(BigDecimal money, String userId) {
		// TODO Auto-generated method stub
		Money money2=findOne(new Money().setUserId(userId));
		if(money2==null) {throw new CommonException(ResultEnum.NOT_FIND_MONEY_TABLE);}
		BigDecimal amount=money2.getTotalIntegral();
		money2.setTotalIntegral(amount.add(money));
		
		moneyDao.save(money2);
		
		//积分增加的途径
		Operation operation=new Operation();
		operation.setMoney(money);
		operation.setOperate(Constant.CONSUME);
		operation.setUserId(userId);
		operationService.insert(operation);
	}


	@Override
	public void delTotalIntegralById(BigDecimal money, String userId) {
		// TODO Auto-generated method stub
		Money money2=findOne(new Money().setUserId(userId));
		if(money2==null) {throw new CommonException(ResultEnum.NOT_FIND_MONEY_TABLE);}
		BigDecimal amount=money2.getTotalIntegral();
		if(amount.compareTo(money)==-1) {throw new CommonException(ResultEnum.CASH_OVER_CONSUME);}
		
		money2.setTotalIntegral(amount.subtract(money));
		moneyDao.save(money2);
		//增加兑换积分的记录
		Operation operation=new Operation();
		operation.setMoney(money);
		operation.setOperate(Constant.DUIHUAN);
		operation.setUserId(userId);
		operationService.insert(operation);
	}


	@Override
	public void deleteByUserId(String userId) {
		// TODO Auto-generated method stub
		Money money=findOne(new Money().setUserId(userId));
		delete(money.getId());
	}
	
	@Override
	@Transactional
	public void withDraw(String id) {
		// TODO Auto-generated method stub
		Operation operation=operationService.findById(id);
		if(operation==null) {throw new CommonException(ResultEnum.ERROR);}
		//提现成功后更改状态
		//delete(id);
		operation.setStatus(1);
		operationService.update(operation);
	}

	@Override
	@Transactional
	public void def(String id) {
		// TODO Auto-generated method stub
		//1删除表中记录
		Operation operation=operationService.findById(id);
		//delete(id);
		operation.setStatus(2);
		operationService.update(operation);
		//2恢复money金额
		addTotalRewardById(operation.getMoney(), operation.getUserId());
	}

	@Override
	public Operation startDraw(String userId, BigDecimal money) {
		// TODO Auto-generated method stub
		//判断提现金额是否大于账户的拥有值
		Money money2=findOne(new Money().setUserId(userId));
		if(money2.getTotalReward().compareTo(money)==-1) {throw new CommonException(ResultEnum.CASH_OVER_CONSUME);}
		delTotalRewardById(money, userId);
		return null;
	}


	@Override
	public List<Money> findUserId(String userId, int page,int size) {
		// TODO Auto-generated method stub
		List moneys=moneyDao.findUserId(userId, PageRequest.of(page, size));
		return parseNoMonthReward(moneys);
	}


	@Override
	public int countUserIds(String userId) {
		// TODO Auto-generated method stub
		return moneyDao.countUserIds(userId);
	}

}
