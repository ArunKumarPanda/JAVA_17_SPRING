package com.adobe.prj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adobe.prj.dao.UserDao;
import com.adobe.prj.entity.Role;
import com.adobe.prj.entity.User;

//@Component
public class UserCrudConfig implements CommandLineRunner {

	@Autowired
	UserDao userDao;
	
	@Override
	public void run(String... args) throws Exception {
		
		userDao.deleteAll();
		
		User u1 = User.builder().name("Rahul").build();
		User u2 = User.builder().name("Swetha").build();
		u1.getRoles().add(Role.builder().role("ADMIN").build());
		u1.getRoles().add(Role.builder().role("GUEST").build());
		
		u2.getRoles().add(Role.builder().role("GUEST").build());
		
		userDao.save(u1);
		userDao.save(u2);
		
		List<User> users = userDao.findAll();
		for(User u : users) {
			System.out.println(u.getName());
			System.out.println(u.getRoles());
		}
		
	}

}
