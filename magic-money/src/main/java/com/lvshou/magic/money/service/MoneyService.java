package com.lvshou.magic.money.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.operation.entity.Operation;

public interface MoneyService {

	public int findMoneyAmount();
	public List<Money> findAll(int page,int size);
	public Money findOne(Money money);
	public Money update(Money money);
	public Money insert(Money money);
	public void delete(String id);
	public void deleteByUserId(String userId);
	public void addMoneyById(BigDecimal money,String userId);
	public void delMoneyById(BigDecimal money,String userId,int op);
	public boolean payimpl(BigDecimal money,String userId,String mode);
	public void addTotalRewardById(BigDecimal money,String userId);
	public void delTotalRewardById(BigDecimal money,String userId);
	public void addTotalIntegralById(BigDecimal money,String userId);
	public void delTotalIntegralById(BigDecimal money,String userId);
	void withDraw(String id);
	void def(String id);
	Operation startDraw(String userId, BigDecimal money);
	/**根据USERID查找*/
	public List<Money> findUserId(String userId,int page,int size);
	public int countUserIds(String userId);
	
	public List<Money> findAllNoPage();
	public List<Money> findMoneyWithMonthReward(int year, int month);
	public List<Money> findMoneyWithMonthRewardPage(int page, int size, String date) throws ParseException;
	public List<Money> findAllNoMonthRewardNoPage();
}
