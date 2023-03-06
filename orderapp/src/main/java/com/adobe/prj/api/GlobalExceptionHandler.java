package com.adobe.prj.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// this advice is for any exception propagated from Controller/RestController
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		var body = new LinkedHashMap<String, Object>();
		body.put("msg", ex.getMessage());
		body.put("timestamp", new Date());
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	/*
	  {
    "errors": [
        "Name is required",
        "Quantity -990 should be more than 0"
    ],
    "timestamp": "2023-03-06T11:09:09.024+00:00"
	}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		var body = new LinkedHashMap<String, Object>();
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(exception -> exception.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors", errors);
		body.put("timestamp", new Date());
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
}
