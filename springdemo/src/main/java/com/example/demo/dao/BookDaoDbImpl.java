package com.example.demo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDaoDbImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("stored using RDBMS!!!");
	}

}
