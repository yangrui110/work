package com.lvshou.magic.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lvshou.magic.entity.Menu;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.service.MenuService;

@Controller
@RequestMapping("menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="rows",defaultValue="10",required=false)int size) {
		return new Result<>(ResultEnum.OK,menuService.findAll(page-1, size));
	}
	
	@ResponseBody
	@PostMapping("insert")
	public Result insert(Menu menu) {
		return new Result<>(ResultEnum.OK,menuService.insert(menu));
	}
	
	@ResponseBody
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable("id")String id) {
		menuService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@ResponseBody
	@PutMapping("update")
	public Result update(Menu menu) {
		return new Result<>(ResultEnum.OK,menuService.update(menu));
	}
	
}
