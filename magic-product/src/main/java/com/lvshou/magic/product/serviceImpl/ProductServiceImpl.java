package com.lvshou.magic.product.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.product.convert.ProductConvert;
import com.lvshou.magic.product.dao.ProductDao;
import com.lvshou.magic.product.entity.Product;
import com.lvshou.magic.product.service.ProductService;
import com.lvshou.magic.product.vo.ProductVo;
import com.lvshou.magic.utils.Convert;
import com.lvshou.magic.utils.MainUUID;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductDao productDao;
	
	@Override
	public List findAll(int page, int size) {
		// TODO Auto-generated method stub
		List list=productDao.findAll(PageRequest.of(page, size)).getContent();
		return ProductConvert.convertList(list);
	}

	@Override
	public ProductVo findOne(ProductVo product) {
		// TODO Auto-generated method stub
		if(product==null) {
			return null;
		}
		if(product.getId()!=null&&!product.getId().equals("")) {
			return ProductConvert.convertToVo(productDao.findById(product.getId()).orElse(null));
		}
		if(product.getName()!=null&&!product.getName().equals("")) {
			return ProductConvert.convertToVo(productDao.findByName(product.getName()).orElse(null));
		}

		return null;
	}

	@Override
	public ProductVo update(ProductVo product) {
		// TODO Auto-generated method stub
		if(product==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		Product product2=productDao.findById(product.getId()).orElse(null);
		if(product2==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(product.getId()!=null) {
			product2.setId(product.getId());
		}
		if(product.getName()!=null) {
			product2.setName(product.getName());
		}
		if(product.getIcon()!=null) {
			product2.setIcon(product.getIcon());
		}
		if(product.getPics()!=null) {
			product2.setPics(Convert.listToString(product.getPics()));
		}
		if(product.getPrice()!=null) {
			product2.setPrice(product.getPrice());
		}
		if(product.getIntegral()!=null) {
			product2.setIntegral(product.getIntegral());
		}
		if(product.getEvaluate()!=null) {
			product2.setEvaluate(product.getEvaluate());
		}
		if(product.getChangeStatus()!=0) {
			product2.setChangeStatus(product.getChangeStatus());
		}
		if(product.getStatus()!=0) {
			product2.setStatus(product.getStatus());
		}
		if(product.getStock()!=0) {
			product2.setStock(product.getStock());
		}
		if(!StringUtils.isEmpty(product.getDetail())) {
			product2.setDetail(product.getDetail());
		}
		if(product.getVipPrice()!=null) {
			product2.setVipPrice(product.getVipPrice());
		}
		productDao.save(product2);
		return ProductConvert.convertToVo(product2);
	}

	@Override
	public ProductVo insert(ProductVo product) {
		// TODO Auto-generated method stub
		if(product==null) {
			return null;
		}
		if(product.getIntegral()==null) {
			product.setIntegral(new BigDecimal(0));
		}
		if(product.getPrice()==null) {
			product.setPrice(new BigDecimal(0));
		}
		if(product.getVipPrice()==null) {
			product.setVipPrice(product.getPrice());
		}
		if(product.getId()==null||product.getId().equals("")) {
			product.setId(MainUUID.getUUID());
		}
		Product ps=new Product();
		BeanUtils.copyProperties(product, ps);
		ps.setPics(Convert.listToString(product.getPics()));
		productDao.save(ps);
		return product;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		if(id==null||id.equals("")) {
			throw new CommonException(ResultEnum.ERROR);
		}
		productDao.deleteById(id);
	}

	@Override
	public ProductVo findById(String id) {
		// TODO Auto-generated method stub
		return ProductConvert.convertToVo(productDao.findById(id).orElse(null));
	}

	@Override
	public int findGoodsCount() {
		// TODO Auto-generated method stub
		
		return productDao.findAll().size();
	}

}
