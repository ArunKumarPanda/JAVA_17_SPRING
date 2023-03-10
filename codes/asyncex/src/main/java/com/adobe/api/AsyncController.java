package com.adobe.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.entity.EmployeeAddresses;
import com.adobe.entity.EmployeeNames;
import com.adobe.entity.EmployeePhone;
import com.adobe.service.AsyncService;

@RestController
public class AsyncController {

	private static Logger log = LoggerFactory.getLogger(AsyncController.class);

	@Autowired
	private AsyncService service;

	@GetMapping("/testAsynch")
	public String testAsynch() throws InterruptedException, ExecutionException {
		log.info("testAsynch Start");

		CompletableFuture<EmployeeAddresses> employeeAddress = service.getEmployeeAddress(); // app thread T1
		CompletableFuture<EmployeeNames> employeeName = service.getEmployeeName(); // T2
		CompletableFuture<EmployeePhone> employeePhone = service.getEmployeePhone(); // T3

		// Wait until they are all done
		CompletableFuture.allOf(employeeAddress, employeeName, employeePhone).join();

		log.info("EmployeeAddress--> " + employeeAddress.get());
		log.info("EmployeeName--> " + employeeName.get());
		log.info("EmployeePhone--> " + employeePhone.get());
		return "done";
	}
}