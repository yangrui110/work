package com.lvshou.magic.menu;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.base.vo.PagedVo;
import com.lvshou.magic.entity.Menu;
import com.lvshou.magic.exception.CommonException;
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
		return new Result<>(ResultEnum.OK,menuService.findAll(page-1, size),menuService.findMenuCount());
	}
	
	@GetMapping("echart")
	public ModelAndView echarts() {
		return new ModelAndView("echart");
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
	
	@GetMapping("add")
	public ModelAndView add(Map<String, Object> map) {
		map.put("menu", new Menu());
		return new ModelAndView("menu_edit");
	}
	@GetMapping("editor/{id}")
	public ModelAndView editor(@PathVariable("id")String id,Map<String, Object> map) {
		Menu menu=menuService.findById(id);
		if(menu==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		map.put("menu", menu);
		return new ModelAndView("menu_edit",map);
	}
	
	@PostMapping("save")
	public ModelAndView save(Menu ad) {
		if(ad.getId()!=null&&!ad.getId().equals("")) {
			update(ad);
		}else {
			insert(ad);
		}
		return new ModelAndView("menuList");
	}
	
	@GetMapping("lists")
	public ModelAndView lists() {
		return new ModelAndView("menuList");
	}
	@ResponseBody
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<Menu> list)  {
		Iterator<Menu> iterator=list.iterator();
		while(iterator.hasNext()) {
			Menu menu=iterator.next();
			menuService.delete(menu.getId());
		}
		return new Result<>(ResultEnum.OK);
	}
}
