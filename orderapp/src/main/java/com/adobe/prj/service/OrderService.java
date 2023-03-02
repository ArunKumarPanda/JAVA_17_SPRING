package com.adobe.prj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public Product getProductById(int id) {
		Optional<Product> opt = productDao.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	public Product addProduct(Product p) {
		return productDao.save(p);
	}
}
