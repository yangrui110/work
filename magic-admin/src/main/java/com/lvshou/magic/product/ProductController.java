package com.lvshou.magic.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.sql.Insert;
import org.mockito.MockitoFramework;
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
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.product.entity.Product;
import com.lvshou.magic.product.service.ProductService;
import com.lvshou.magic.product.vo.ProductVo;
import com.lvshou.magic.result.Result;

@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@ResponseBody
	@GetMapping("findAll")
	public Result findAll(@RequestParam(value="page",defaultValue="1",required=false) int page,
			@RequestParam(value="rows",defaultValue="10",required=false) int rows) {
		
		return new Result<>(ResultEnum.OK, productService.findAll(page-1, rows),productService.findGoodsCount());
	}
	
	@ResponseBody
	@PostMapping("insert")
	public Result insert(ProductVo productVo) {
		return new Result<>(ResultEnum.OK, productService.insert(productVo));
	}
	@ResponseBody
	@PutMapping("update")
	public Result update(ProductVo productVo) {
		return new Result<>(ResultEnum.OK, productService.update(productVo));
	}
	
	@ResponseBody
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable("id") String id) {
		productService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
	
	@GetMapping("edit/{id}")
	public ModelAndView editor(@PathVariable("id")String id,Map<String, Object> map) {
		map.put("product",productService.findById(id));
		return new ModelAndView("productEditor",map);
	}
	
	@PostMapping("save")
	public ModelAndView save(ProductVo vo,Map<String, Object> map) {
		if(vo.getId()==null||vo.getId().equals("")) {
			productService.insert(vo);
			map.put("product", new ProductVo());
			return new ModelAndView("productEditor",map);
			
		}else
			productService.update(vo);
		return new ModelAndView("productsList");
	}
	@GetMapping("products")
	public ModelAndView products() {
		return new ModelAndView("productsList");
	}
	@GetMapping("add")
	public ModelAndView add(Map<String, Object> map) {
		map.put("product", new ProductVo());
		return new ModelAndView("productEditor",map);
	}
	@ResponseBody
	@GetMapping("findById")
	public Result findById(@RequestParam("id")String id) {
		List list=new ArrayList<>();
				list.add(productService.findById(id));
		return new Result<>(ResultEnum.OK,list);
	}
	@ResponseBody
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<Product> products) {
		Iterator<Product> iterator=products.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();
			productService.delete(product.getId());
		}
		return new Result<>(ResultEnum.OK);
	}
}
