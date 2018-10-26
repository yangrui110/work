package com.lvshou.magic.product;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.lvshou.magic.entity.Advertise;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.service.AdvertiseService;

@Controller
@RequestMapping("advertise")
public class AdvertiseController {

	@Autowired
	private AdvertiseService advertiseService;
	@ResponseBody
	@GetMapping("findAll")
	public PagedVo findAll(@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="size",defaultValue="10",required=false)int size) {
		return new PagedVo(advertiseService.findAverAmount(),advertiseService.findAll(page-1, size));
	}
	@ResponseBody
	@PostMapping("insert")
	public Result insert(Advertise vo) {
		if(vo==null) {return new Result<>(ResultEnum.ERROR);}
		advertiseService.insert(vo);
		return new Result<>(ResultEnum.OK,advertiseService.insert(vo));
	}
	@ResponseBody
	@PutMapping("update")
	public Result update(Advertise vo) {
		
		return new Result<>(ResultEnum.OK,advertiseService.update(vo));
	}
	@ResponseBody
	@GetMapping("findByType")
	public Result findByType(@RequestParam(value="type",defaultValue="0",required=false)int type,
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="rows",defaultValue="10",required=false)int size){
		return new Result<>(ResultEnum.OK,advertiseService.findByType(type,page-1,size),advertiseService.findByTypeAmount(type));
	}
	
	@DeleteMapping("adDelete/{id}")
	public ModelAndView adDelete(@PathVariable("id")String id) {
		advertiseService.delete(id);
		return new ModelAndView("advertiseList");
	}
	@DeleteMapping("backDelete/{id}")
	public ModelAndView backDelete(@PathVariable("id")String id) {
		advertiseService.delete(id);
		return new ModelAndView("background");
	}
	@DeleteMapping("introduceDelete/{id}")
	public ModelAndView introduceDelete(@PathVariable("id")String id) {
		advertiseService.delete(id);
		return new ModelAndView("introduce");
	}
	@GetMapping("editor/{id}")
	public ModelAndView editor(@PathVariable("id")String id,Map<String, Advertise> map) {
		Advertise advertise=new Advertise();
		if(id!=null&&!id.equals("")) {
			advertise=advertiseService.findById(id);
		}
		map.put("advertise", advertise);
		return new ModelAndView("advertise_edit",map);
	}
	///增加广告图时，需要传入广告图的类型
	@GetMapping("add")
	public ModelAndView add(@RequestParam("type")int type,Map<String, Advertise> map) {
		Advertise advertise=new Advertise();
		advertise.setType(type);
		map.put("advertise", advertise);
		return new ModelAndView("advertise_edit",map);
	}
	@GetMapping("lists")
	public ModelAndView lists() {
		return new ModelAndView("advertiseList");
	}
	@PostMapping("save")
	public ModelAndView save(Advertise ad) {
		if(ad.getId()!=null&&!ad.getId().equals("")) {
			advertiseService.update(ad);
		}else {
			advertiseService.insert(ad);
			switch (ad.getType()) {
			case 1:
				return new ModelAndView("advertiseList");
			case 2:
				return new ModelAndView("background");
			case 3:
				return new ModelAndView("introduce");
			}
		}
		return new ModelAndView("advertiseList");
	}
	@GetMapping("adverLists")
	public ModelAndView adverLists() {
		return new ModelAndView("advertiseList");
	}
	@GetMapping("backLists")
	public ModelAndView backLists() {
		return new ModelAndView("background");
	}
	@GetMapping("introduceLists")
	public ModelAndView introduceLists() {
		return new ModelAndView("introduce");
	}
	@ResponseBody
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<Advertise> ids) {
		//开始删除所有的数据
		Iterator<Advertise> iterator=ids.iterator();
		while(iterator.hasNext()) {
			Advertise advertise=iterator.next();
			advertiseService.delete(advertise.getId());
		}
		return new Result<>(ResultEnum.OK);
	}
	
}
