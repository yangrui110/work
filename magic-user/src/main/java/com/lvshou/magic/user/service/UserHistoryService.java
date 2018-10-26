package com.lvshou.magic.user.service;

import java.text.ParseException;
import java.util.List;

import com.lvshou.magic.user.entity.UserHistory;

public interface UserHistoryService {

	/**按照月份查找
	 * @throws Exception */
	public List<UserHistory> findMonths(String date,int page,int size) throws Exception;
	public int countMonths(String date) throws ParseException;
	public List<UserHistory> findMonthNoPage(String date) throws ParseException;
	/**按照姓名查找*/
	public List<UserHistory> findNames(String name,int page,int size);
	public int countNames(String names);
	/**按照编号查找*/
	public List<UserHistory> findNumIds(String numId,int page,int size);
	public int countNumIds(String numId);
	/**按照手机号查找*/
	public List<UserHistory> findPhones(String phones,int page,int size);
	public int countPhones(String phones);
	
	/**查找所有的*/
	public List<UserHistory> findAll(int page,int size);
	public int countAll();
	
	/**没有分页查找*/
	public List<UserHistory> findAllNoPage();
}
