package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {
	// find all products where quantity is zero
	// select * from products where qty = ?
	List<Product> findByQuantity(int qty);
	// select * from products where qty =? and category =?
	// List<Product> findByQuantityAndCategory(int qty, String category);
	// select * from products where qty =? or category =?
	// List<Product> findByQuantityOrCategory(int qty, String category);
	
	// select * from products where price >= low and price <= high
	@Query("from Product where price >= :l and price <= :h")
	//@Query(value = "select * from  products where price >= :l && price <= :h", nativeQuery = true)
	List<Product> getByRange(@Param("l") double low, @Param("h") double high);
}
