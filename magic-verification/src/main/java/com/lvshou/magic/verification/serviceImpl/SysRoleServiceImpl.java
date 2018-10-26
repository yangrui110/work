package com.lvshou.magic.verification.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvshou.magic.verification.dao.SysRoleDao;
import com.lvshou.magic.verification.entity.SysRole;
import com.lvshou.magic.verification.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public SysRole findById(String id) {
		// TODO Auto-generated method stub
		return sysRoleDao.findById(id).orElse(null);
	}

}
