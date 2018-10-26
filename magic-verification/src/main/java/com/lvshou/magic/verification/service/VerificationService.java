package com.lvshou.magic.verification.service;

import org.springframework.data.domain.Page;

import com.lvshou.magic.verification.entity.Verification;

public interface VerificationService {

	public int findVerificationAmount();
	public boolean login(String account,String password);
	public boolean logout();
	public Page<Verification> findAll(int page,int size);
	public void delete(String id);
	public void insert(Verification verification);
	public Verification update(Verification verification);
	public Verification findOne(String id);
	public Verification findByAccount(String account);
	/**第三方登录成功*/
	public Verification loginSuccess(String username,String password,String sysId);
}
