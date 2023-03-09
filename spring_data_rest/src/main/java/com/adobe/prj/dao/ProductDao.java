package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.adobe.prj.entity.Product;

@RepositoryRestResource(path="products", collectionResourceRel = "items")
public interface ProductDao extends JpaRepository<Product, Integer> {
	List<Product> findByQuantity(int qty);
	@Query("from Product where price >= :l and price <= :h")
	List<Product> getByRange(@Param("l") double low, @Param("h") double high);
	
	@Modifying
	@Query("update Product set price = :pr where id = :id")
	void updateProduct(@Param("id") int id, @Param("pr") double price);
}
