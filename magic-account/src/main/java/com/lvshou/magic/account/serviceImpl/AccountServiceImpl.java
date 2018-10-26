package com.lvshou.magic.account.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.account.dao.AccountDao;
import com.lvshou.magic.account.entity.Account;
import com.lvshou.magic.account.service.AccountService;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.utils.MainUUID;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountDao accountDao;
	
	@Override
	public Account findOne(Account account) {
		// TODO Auto-generated method stub
		if(account.getId()!=null) {
			return accountDao.findById(account.getId()).orElse(null);
		}else if (account.getUserId()!=null) {
			Account account2=accountDao.findByUserId(account.getUserId());

			return account2;
		}else if (account.getAccount()!=null) {
			Account account2=findByAccount(account.getAccount());
			return account2;
		}else {
			return null;
		}
	}

	@Override
	public Page<Account> findAll(int page,int size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest=PageRequest.of(page, size);
		return accountDao.findAll(pageRequest);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id!=null) {
			try {
				accountDao.deleteById(id);
			} catch (Exception e) {
				// TODO: handle exception
				throw new CommonException(ResultEnum.ID_NOT_EXIT);
			}
			
		}
	}

	@Override
	public void insert(Account account) {
		// TODO Auto-generated method stub
		if(account!=null) {
			if(account.getId()==null) {
				account.setId(MainUUID.getUUID());
			}
		}
		accountDao.save(account);
	}

	public Account findById(String id) {
		return accountDao.findById(id).orElse(null);
	}
	
	@Override
	public Account update(Account account) {
		// TODO Auto-generated method stub
		if(account==null) {throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);}
		Account account2=findById(account.getId());
		
		if(account2!=null) {
			if(account.getAccount()!=null) {
				account2.setAccount(account.getAccount());
			}
			if(account.getUserId()==null) {
				account2.setUserId(account.getUserId());
			}
			if(account.getType()!=null) {
				account2.setType(account.getType());
			}
			
			accountDao.save(account2);
			return account2;
		}
		
		return null;
	}

	@Override
	public Account findByAccount(String account) {
		// TODO Auto-generated method stub
		return accountDao.findByAccount(account);
	}

	@Override
	public Account findByUserId(String userId) {
		// TODO Auto-generated method stub
		return accountDao.findByUserId(userId);
	}

	@Override
	public int findAccountAmount() {
		// TODO Auto-generated method stub
		return accountDao.findAll().size();
	}
	
	
}
