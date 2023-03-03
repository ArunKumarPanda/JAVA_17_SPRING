package com.adobe.prj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adobe.prj.entity.Customer;
import com.adobe.prj.entity.Item;
import com.adobe.prj.entity.Order;
import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

@Component
public class OrderCrudConfig implements CommandLineRunner {
	@Autowired
	OrderService service;
	
	@Override
	public void run(String... args) throws Exception {
		Order order = new Order();
		order.setCustomer(Customer.builder().email("b@adobe.com").build());
		List<Item> items = new ArrayList<>();
		Item i1 = new Item();
		i1.setProduct(Product.builder().id(1).build());
		i1.setQuantity(1);
		
		Item i2 = new Item();
		i2.setProduct(Product.builder().id(2).build());
		i2.setQuantity(3);
		
		items.add(i1);
		items.add(i2);
		
		order.setItems(items);
		
		service.placeOrder(order);
	}

}
