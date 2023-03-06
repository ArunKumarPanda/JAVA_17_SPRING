package com.adobe.prj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adobe.prj.api.ResourceNotFoundException;
import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

//@Component
public class ProductCrudConfig implements CommandLineRunner{
	@Autowired
	private OrderService service;
	
	@Override
	public void run(String... args) throws Exception {
		//addProducts();
//		listProducts();
//		getById();
//		getByRange();
//		updateProduct();
	}

	private void updateProduct() throws ResourceNotFoundException {
		Product p = service.updateProduct(2, 982.00);
		System.out.println(p);
	}

	private void getByRange() {
		List<Product> products = service.getByRange(500, 1000);
		for(Product p : products) {
			System.out.println(p); // toString
		}
	}
	private void getById() throws ResourceNotFoundException {
		System.out.println("By ID ----->");
		Product p = service.getProductById(1);
		System.out.println(p);
	}

	private void listProducts() {
		List<Product> products = service.getProducts();
		for(Product p : products) {
			System.out.println(p); // toString
		}
	}

	private void addProducts() {
		Product p1 = Product.builder()
					.name("iPhone 14")
					.price(98000.00)
					.quantity(100)
					.build();
		
		Product p2 = Product.builder()
				.name("Logitech mouse")
				.price(800.00)
				.quantity(100)
				.build();
		
		service.addProduct(p1);
		service.addProduct(p2);
		
	
		
	}

}
