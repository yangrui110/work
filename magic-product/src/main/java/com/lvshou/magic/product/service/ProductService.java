package com.lvshou.magic.product.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lvshou.magic.product.vo.ProductVo;

public interface ProductService {

	public List findAll(int page,int size);
	public ProductVo findOne(ProductVo product);
	public ProductVo update(ProductVo product);
	public ProductVo insert(ProductVo product);
	public void delete(String id);
	public ProductVo findById(String id);
	public int findGoodsCount();
}
