package com.adobe.prj;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Product;

@BasePathAwareController
public class ProductController {
	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(path = "products", method = RequestMethod.GET)
	//@GetMapping()
	public @ResponseBody List<Product> getProducts() {
		return Arrays.asList(Product.builder().id(1).name("A").build(), Product.builder().id(2).name("B").build());	
	}
}
