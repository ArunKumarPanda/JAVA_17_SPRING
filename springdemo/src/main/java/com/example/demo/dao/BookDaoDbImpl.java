package com.example.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

//@Profile("dev")
@ConditionalOnProperty(name = "dao", havingValue = "db")
@Repository("db")
public class BookDaoDbImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("stored using RDBMS!!!");
	}

}
