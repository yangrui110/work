package com.lvshou.magic.verification.serviceImpl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.verification.dao.VerificationDao;
import com.lvshou.magic.verification.entity.SysRole;
import com.lvshou.magic.verification.entity.Verification;
import com.lvshou.magic.verification.service.SysRoleService;
import com.lvshou.magic.verification.service.VerificationService;

@Service
public class VerificationServerImpl implements VerificationService {

	@Autowired
	VerificationDao verificationDao;
	@Autowired
	SysRoleService sysRoleService;
	
	@Override
	public boolean login(String account, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<Verification> findAll(int page,int size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest=PageRequest.of(page, size);
		return verificationDao.findAll(pageRequest);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id!=null) {
			try {
				verificationDao.deleteById(id);
			} catch (Exception e) {
				// TODO: handle exception
				throw new CommonException(ResultEnum.ID_NOT_EXIT);
			}
		}
	}

	@Override
	public void insert(Verification verification) {
		// TODO Auto-generated method stub
		if(verification!=null) {
			if(verification.getId()==null) {
				verification.setId(MainUUID.getUUID());
			}
			
		}
		verificationDao.save(verification);
	}

	@Override
	public Verification update(Verification verification) {
		// TODO Auto-generated method stub
		if(verification==null) {throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);}
		Verification verification2=findOne(verification.getId());
		if(verification2!=null) {
			if(verification.getAccount()!=null) {
				verification2.setAccount(verification.getAccount());
			}
			if(verification.getPassword()!=null) {
				verification2.setPassword(verification.getPassword());
			}
			verificationDao.save(verification2);
			return verification2;
		}
		return null;
		
	}

	@Override
	public Verification findOne(String id) {
		// TODO Auto-generated method stub
		return verificationDao.findById(id).orElse(null);
	}

	@Override
	public Verification findByAccount(String account) {
		// TODO Auto-generated method stub
		return verificationDao.findByAccount(account);
	}

	@Override
	public int findVerificationAmount() {
		// TODO Auto-generated method stub
		return verificationDao.findAll().size();
	}

	@Override
	public Verification loginSuccess(String username, String password,String sysId) {
		// TODO Auto-generated method stub
		if(verificationDao.findByAccount(username)!=null) {
			return null;
		}
		Verification verification=new Verification();
		verification.setAccount(username);
		verification.setPassword(password);
		/*第三方默认是用户*/
		SysRole sysRole=sysRoleService.findById(sysId);
		List list=new LinkedList<SysRole>();
		list.add(sysRole);
		verification.setRoles(list);
		insert(verification);
		return verification;
	}
	
	
	

}
