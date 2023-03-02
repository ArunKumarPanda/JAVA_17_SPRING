package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.service.BookService;

@SpringBootApplication
public class SpringdemoApplication implements CommandLineRunner {

	@Autowired
	private BookService service;
	
	public static void main(String[] args) {
		/* ApplicationContext ctx = */
		SpringApplication.run(SpringdemoApplication.class, args);
		
//		String[] names = ctx.getBeanDefinitionNames();
//		for(String name : names) {
//			System.out.println(name);
//		}
		
		/*
		 * BookService service = ctx.getBean("bookService", BookService.class);
		 * service.insert();
		 */
	}

	@Override
	public void run(String... args) throws Exception {
		service.insert();
	}

}
