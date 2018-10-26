package com.lvshou.magic.account.service;

import org.springframework.data.domain.Page;

import com.lvshou.magic.account.entity.Account;


public interface AccountService {

	public int findAccountAmount();
	public Account findOne(Account account);
	public Account findByAccount(String account);
	public Page<Account> findAll(int page,int size);
	public void delete(String id);
	public void insert(Account account);
	public Account update(Account account);
	public Account findByUserId(String userId);
	
}
