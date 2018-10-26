package com.lvshou.magic.product.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lvshou.magic.product.entity.Product;
import com.lvshou.magic.product.vo.ProductVo;
import com.lvshou.magic.utils.Convert;

public class ProductConvert {

	public static ProductVo convertToVo(Product product) {
		if(product==null) {
			return null;
		}
		ProductVo productVo=new ProductVo();
		BeanUtils.copyProperties(product, productVo);
		productVo.setPics(Convert.stringToList(product.getPics()));
		return productVo;
	}
	
	public static List<ProductVo> convertList(List list){
		List<ProductVo> list2=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			list2.add(convertToVo((Product)list.get(i)));
		}
		return list2;
	}
}
