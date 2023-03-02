package com.example.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;



//@Profile("prod")
@ConditionalOnProperty(name = "dao", havingValue = "mongo")
@Repository("mongo")
public class BookDaoMongoImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Mongodb store!!!");
	}

}
