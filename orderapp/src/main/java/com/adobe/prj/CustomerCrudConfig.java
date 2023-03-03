package com.adobe.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.adobe.prj.entity.Customer;
import com.adobe.prj.service.OrderService;

//@Component

public class CustomerCrudConfig implements CommandLineRunner {
	@Autowired
	OrderService service;

	@Override
	public void run(String... args) throws Exception {
		service.addCustomer(Customer.builder().email("a@adobe.com").firstName("Asha").build());
		service.addCustomer(Customer.builder().email("b@adobe.com").firstName("Beena").build());
			
	}

}
