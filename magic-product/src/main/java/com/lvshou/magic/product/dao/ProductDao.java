package com.lvshou.magic.product.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.product.entity.Product;

public interface ProductDao extends JpaRepository<Product, String>{

	@Query(value="select * from product order by create_time DESC",nativeQuery=true)
	public Page<Product> findAll(Pageable pageable);
	public Optional<Product> findByName(String name);
	public List findAll();
	@Query(value="select count(*) from product",nativeQuery=true)
	public int findProductsAmount();
	
}
