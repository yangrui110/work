package com.lvshou.magic.referral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.base.vo.PagedVo;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.referral.entity.Referral;
import com.lvshou.magic.referral.service.ReferralService;
import com.lvshou.magic.result.Result;

@RestController
@RequestMapping("referral")
public class ReferralController {

	@Autowired
	ReferralService referralService;
	
	@RequestMapping("findAll")
	public PagedVo findAll() {
		return new PagedVo(referralService.findReferralAmount(), referralService.findAll(0,10).getContent());
	}
	
	@RequestMapping("findAllParent/{id}")
	public Result findAllParent(@PathVariable String id) {
		return new Result<>(ResultEnum.OK, referralService.findAllParent(id));
	}
	
	@RequestMapping("findAllChild/{id}")
	public Result findAllChild(@PathVariable String id) {
		
		return new Result<>(ResultEnum.OK, referralService.findAllChild(id));
	}
	
	@RequestMapping("find/{id}")
	public Result findOne(@PathVariable String id) {
		
		return new Result<>(ResultEnum.OK, referralService.findOne(id));
	}
	
	@RequestMapping("update/{id}")
	public Result update(@PathVariable String id) {
		
		Referral referral=new Referral();
		referral.setUserId("12399");
		referral.setParentId("12345");
		referral.setId(id);
		return new Result<>(ResultEnum.OK, referralService.update(referral));
	}
	
	@RequestMapping("insert")
	public Result insert() {
		Referral referral=new Referral();
		referral.setChildId("12345");
		referral.setParentId(null);
		referral.setUserId("12399");
		return new Result<>(ResultEnum.OK,referralService.insert(referral));
	}
	
}
