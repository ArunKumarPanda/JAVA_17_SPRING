package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BookDao;

@Service
public class BookService {
	
	@Autowired
	private BookDao bookDao;
	
	public void insert() {
		this.bookDao.addBook();
	}
}
