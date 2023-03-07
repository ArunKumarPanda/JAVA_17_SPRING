package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
@Validated
@Tag(name="products", description="Product API")
public class ProductController {
	@Autowired
	private OrderService service;
	

	// http://localhost:8080/api/products
	// GET http://localhost:8080/api/products?low=1000&high=50000
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low, 
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if(low == 0.0 && high == 0.0) {
			return service.getProducts();
		} else {
			return service.getByRange(low, high);
		}
	}

	@Operation(summary="Get Product by ID")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", 
//					description = "found the product", 
//					content = {@Content(mediaType="application/json", 
//					schema = @Sch(implementation=Product.class))})
//			})

	// http://localhost:8080/api/products/2
	@GetMapping("/{id}")
	public @ResponseBody Product getProduct(@PathVariable("id") int id) throws ResourceNotFoundException {
		return service.getProductById(id);
	}
	
	// POST {name:"A", "price":11} ==> content-type:application/json
	@PostMapping()
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
		p = service.addProduct(p);
		return new ResponseEntity<Product>(p, HttpStatus.CREATED);
	}
	
	// PUT http://localhost:8080/api/products/1
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") int id, @RequestBody Product p) throws ResourceNotFoundException {
		return service.updateProduct(id, p.getPrice());
	}
}
