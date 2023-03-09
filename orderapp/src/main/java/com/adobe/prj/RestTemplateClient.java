package com.adobe.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adobe.prj.entity.Product;

@Configuration
public class RestTemplateClient implements CommandLineRunner {

	@Autowired
	RestTemplate template;

	
	@Override
	public void run(String... args) throws Exception {
//		getUsers();
		getProduct();
		addProduct();
	}


	private void getUsers() {
		String json = template.getForObject("https://jsonplaceholder.typicode.com/users", String.class);
		System.out.println(json);
	}

	private void getProduct() {
		ResponseEntity<Product> response = template.getForEntity("http://localhost:8080/api/products/2", Product.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
	}
	
	private void addProduct() {
		Product p = new Product(0, "Tata Play", 45000.00, 100, 0);
		
		ResponseEntity<Product> response= 
				template.postForEntity("http://localhost:8080/api/products", p, Product.class);
		System.out.println(response.getStatusCode()); // 201
	}
}
