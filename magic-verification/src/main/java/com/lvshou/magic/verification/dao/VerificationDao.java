package com.lvshou.magic.verification.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lvshou.magic.verification.entity.Verification;

public interface VerificationDao extends JpaRepository<Verification, String> {

	public Optional<Verification> findById(String id);
	public List<Verification> findByAccountAndPassword(String account,String password);
	public Page<Verification> findAll(Pageable pageable);
	
	public Verification findByAccount(String account);
}
