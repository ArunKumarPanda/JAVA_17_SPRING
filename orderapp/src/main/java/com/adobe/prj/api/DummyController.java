package com.adobe.prj.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.service.OrderService;

@RestController
@RequestMapping("/hello")
public class DummyController {
	
	@Autowired
	private OrderService service;
	
	@GetMapping()
	public void hello() {
		service.doTask();
	}
}
