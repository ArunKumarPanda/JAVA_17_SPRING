package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
@Validated
public class ProductController {
	@Autowired
	private OrderService service;
	
	@Autowired
	private ObservationRegistry observationRegistry;

	
	@GetMapping("/cache/{id}")
	public ResponseEntity<Product> getProductCache(@PathVariable("id") int id) throws ResourceNotFoundException {
		Product p = service.getProductById(id);
		return ResponseEntity.ok().eTag(Long.toString(p.hashCode())).body(p);
	}
	
	// http://localhost:8080/api/products
	// GET http://localhost:8080/api/products?low=1000&high=50000
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low, 
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if(low == 0.0 && high == 0.0) {
			return Observation.createNotStarted("getProducts", observationRegistry)
					.observe(() -> service.getProducts());
		} else {
			return service.getByRange(low, high);
		}
	}

	@Cacheable(value="productCache", key ="#id")
	@GetMapping("/{id}")
	public @ResponseBody Product getProduct(@PathVariable("id") int id) throws ResourceNotFoundException {
		System.out.println("Cache Miss!!!");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return service.getProductById(id);
	}
	
	// POST {name:"A", "price":11} ==> content-type:application/json
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
		p = service.addProduct(p);
		return new ResponseEntity<Product>(p, HttpStatus.CREATED);
	}
	
	@CachePut(value="productCache", key ="#id")
	// PUT http://localhost:8080/api/products/1
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") int id, @RequestBody Product p) throws ResourceNotFoundException {
		return service.updateProduct(id, p.getPrice());
	}
	
	// Avoid
	@CacheEvict(value="productCache", key="#id")
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		return "deleted!!!";
	}
}
