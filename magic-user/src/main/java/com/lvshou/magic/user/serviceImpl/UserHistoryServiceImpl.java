package com.lvshou.magic.user.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lvshou.magic.user.dao.UserHistoryDao;
import com.lvshou.magic.user.entity.UserHistory;
import com.lvshou.magic.user.service.UserHistoryService;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

	@Autowired
	private UserHistoryDao userHistoryDao;
	
	@Override
	public List<UserHistory> findMonths(String date,int page,int size) throws Exception {
		// TODO Auto-generated method stub
		Calendar calendar=parseCalender(date);
		return userHistoryDao.findMonth(calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR),PageRequest.of(page-1, size));
	}
	
	private Calendar parseCalender(String date) throws ParseException {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
		Date date2=dateFormat.parse(date);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date2);
		return calendar;
	}

	@Override
	public int countMonths(String date) throws ParseException {
		// TODO Auto-generated method stub
		Calendar calendar=parseCalender(date);
		return userHistoryDao.countMonth(calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR));
	}

	@Override
	public List<UserHistory> findNames(String name,int page,int size) {
		// TODO Auto-generated method stub
		return userHistoryDao.findNames(name,PageRequest.of(page-1, size));
	}

	@Override
	public int countNames(String names) {
		// TODO Auto-generated method stub
		return userHistoryDao.countNames(names);
	}

	@Override
	public List<UserHistory> findNumIds(String numId,int page,int size) {
		// TODO Auto-generated method stub
		return userHistoryDao.findNumIds(numId,PageRequest.of(page-1, size));
	}

	@Override
	public int countNumIds(String numId) {
		// TODO Auto-generated method stub
		return userHistoryDao.countNumIds(numId);
	}

	@Override
	public List<UserHistory> findPhones(String phones,int page,int size) {
		// TODO Auto-generated method stub
		return userHistoryDao.findPhones(phones,PageRequest.of(page-1, size));
	}

	@Override
	public int countPhones(String phones) {
		// TODO Auto-generated method stub
		return userHistoryDao.countPhones(phones);
	}

	@Override
	public List<UserHistory> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return userHistoryDao.findAlls(PageRequest.of(page-1, size));
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return userHistoryDao.countAll();
	}

	@Override
	public List<UserHistory> findAllNoPage() {
		// TODO Auto-generated method stub
		List<UserHistory> lists=userHistoryDao.findAll();
		return lists;
	}

	@Override
	public List<UserHistory> findMonthNoPage(String date) throws ParseException {
		// TODO Auto-generated method stub
		Calendar calendar=parseCalender(date);
		return userHistoryDao.findMonthNoPage(calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR));
	}

}
