package com.lvshou.magic.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.money.entity.Reward;
import com.lvshou.magic.money.service.RewardService;
import com.lvshou.magic.result.Result;

@RestController
@RequestMapping("reward")
public class RewardController {

	@Autowired
	private RewardService rewardService;
	
	@GetMapping("findAll")
	public Result findAll(
			@RequestParam(value="targetName",required=false)String targetName,
			@RequestParam(value="targetPhone",required=false)String targetPhone,
			@RequestParam(value="sourceName",required=false)String sourceName,
			@RequestParam(value="sourcePhone",required=false)String sourcePhone,
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="rows",defaultValue="10",required=false)int size) {
		Reward reward=new Reward();
		reward.setTargetName(targetName);reward.setTargetPhone(targetPhone);reward.setSourceName(sourceName);reward.setSourcePhone(sourcePhone);
		if(StringUtils.isEmpty(targetName)&&StringUtils.isEmpty(targetPhone)&&StringUtils.isEmpty(sourceName)&&StringUtils.isEmpty(sourcePhone)) {
			return new Result<>(ResultEnum.OK,rewardService.findAll(page-1, size),rewardService.findRewardAmount());
		}
		return new Result<>(ResultEnum.OK,rewardService.findRewards(reward, page-1, size),rewardService.counts(reward));
	}
	
	@GetMapping("findByTarget/{id}")
	public Result finds(@PathVariable("id")String id) {
		return new Result<>(ResultEnum.OK,rewardService.findByTarget(id));
	}
	
	@GetMapping("findByTargetPhone")
	public Result findByPhone(@RequestParam("phone")String phone,
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="rows",defaultValue="10",required=false)int size) {
		return new Result<>(ResultEnum.OK,rewardService.findByTargetPhone(phone,page-1,size));
	}
	
	@GetMapping("lists")
	public ModelAndView finds() {
		return new ModelAndView("reward");
	}
}
