package com.example.demo.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;



@Profile("prod")
@Repository("mongo")
public class BookDaoMongoImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Mongodb store!!!");
	}

}
