package com.lvshou.magic.account.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lvshou.magic.account.entity.Account;


public interface AccountDao extends JpaRepository<Account, String> {

	public Optional<Account> findById(String id);
	
	public Account findByAccount(String account);
	public Account findByUserId(String userId);
	public List<Account> findByAccountAndType(String account,String type);
	public Page<Account> findAll(Pageable pageable);
}
