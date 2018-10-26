package com.lvshou.magic.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.base.vo.PagedVo;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.verification.entity.Verification;
import com.lvshou.magic.verification.service.VerificationService;

@RestController
@RequestMapping("verification")
public class VerifictionController {

	@Autowired
	VerificationService verificationService;
	
	@RequestMapping("findAll")
	public PagedVo findAll() {
		return new PagedVo(verificationService.findVerificationAmount(),verificationService.findAll(0, 3).getContent());
	}
	
	@RequestMapping("delete/{id}")
	public Result delete(@PathVariable String id) {
		verificationService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@RequestMapping("insert")
	public Result insert() {
		Verification verification=new Verification();
		verification.setAccount("134167");
		verification.setPassword("134167");
		verificationService.insert(verification);
		return new Result<>(ResultEnum.OK, verification);
	}
	
	@RequestMapping("update/{id}")
	public Result update(@PathVariable String id) {
		
		Verification verification=new Verification();
		verification.setAccount("13416721");
		verification.setId(id);
		return new Result<>(ResultEnum.OK,verificationService.update(verification));
	}
}
