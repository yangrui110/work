package com.lvshou.magic.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.account.entity.Account;
import com.lvshou.magic.account.service.AccountService;
import com.lvshou.magic.base.vo.PagedVo;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/find")
	public Result findAccount() {
		Account account=new Account();
		account.setId("1345167");
		account=accountService.findOne(account);
		return new Result<>(ResultEnum.OK, account);
	}
	
	@RequestMapping("/findAll")
	public PagedVo findAll() {
		Page<Account> page=accountService.findAll(0, 10);
		return new PagedVo(accountService.findAccountAmount(), page.getContent());
	}
	
	@RequestMapping("/delete/{id}")
	public Result delAccount(@PathVariable String id) {
		accountService.delete("1345167");
		return new Result<>(ResultEnum.OK);
	}
	@RequestMapping("/update/{id}")
	public Result updateAccount(@PathVariable String id) {
		Account account=new Account();
		account.setId(id);
		account.setAccount("15672498519");
		if(account!=null) {
			account=accountService.update(account);
		}
		/*更新时候的account的属性值不能为空*/
		return new Result<>(ResultEnum.OK,account);
	}
	@RequestMapping("/insert")
	public Result insertAccount() {
		Account account=new Account();
		account.setUserId("12345670");
		account.setAccount("15672498519");
		account.setType("zhifubao");
		accountService.insert(account);
		return new Result<>(ResultEnum.OK, account);
	}
	@ResponseBody
	@PostMapping
	public Result deleteAll(@RequestBody List<Account> accounts) {
		accounts.forEach(account->{
			accountService.delete(account.getId());
		});
		return new Result<>(ResultEnum.OK);
	}
}
