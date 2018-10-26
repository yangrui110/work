package com.lvshou.magic.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.entity.UserHistory;
import com.lvshou.magic.user.export.ExportUserHistory;
import com.lvshou.magic.user.service.UserHistoryService;

@Controller
@RequestMapping("userHistory")
public class UserHistoryController {

	@Autowired
	private UserHistoryService userHistoryService;
	
	@GetMapping("list")
	public ModelAndView list() {
		return new ModelAndView("history");
	}
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(@RequestParam(name="page",defaultValue="1",required=false)int page,
			@RequestParam(name="limit",defaultValue="10",required=false)int size,
			@RequestParam(name="date",required=false)String date,
			@RequestParam(name="name",required=false)String name,
			@RequestParam(name="phone",required=false)String phone,
			@RequestParam(name="numId",required=false)String numId) throws Exception {
		
		List<UserHistory> lists=null;
		int count=0;
		if(!StringUtils.isEmpty(date)) {
			lists=userHistoryService.findMonths(date, page, size);
			count=userHistoryService.countMonths(date);
		}else if (!StringUtils.isEmpty(name)) {
			lists=userHistoryService.findNames(name, page, size);
			count=userHistoryService.countNames(name);
		}else if (!StringUtils.isEmpty(phone)) {
			lists=userHistoryService.findPhones(phone, page, size);
			count=userHistoryService.countPhones(phone);
		}else if (!StringUtils.isEmpty(numId)) {
			lists=userHistoryService.findNumIds(numId, page, size);
			count=userHistoryService.countNumIds(numId);
		}else {
			lists=userHistoryService.findAll(page, size);
			count=userHistoryService.countAll();
		}
		return new Result<>(ResultEnum.OK,lists,count);
	}
	
	@ResponseBody
	@GetMapping("export")
	public void export(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="date",required=false)String date) throws Exception {
		List<UserHistory> lists=null;
		if(!StringUtils.isEmpty(date))
			lists=userHistoryService.findMonthNoPage(date);
		else lists=userHistoryService.findAllNoPage();
		new ExportUserHistory().export(request, response, lists);
	}
	
}
