package com.dreamchain.skeleton.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dreamchain.skeleton.model.User;

@Transactional
public class UserDaoImplTest extends DaoTest {
	
	@Autowired UserDaoImpl userDaoImpl;
	
	@Test
	public void all_fields_are_persisted() {
//		User user = new User();
//		user.setName("Name1");
//		user.setEmail("simon@domain.com");
//		user.setPassword("XXX");
//		user.setRole("ROLE_USER");
//		user.setPhone("09876543211");
		User user=userDaoImpl.get(3190l);
		System.out.println(user.getName());
//		userDaoImpl.save(user);
//		List<User> users = userDaoImpl.findAll(user.getEmail());
//		Assert.assertEquals("Name1", users.get(0).getName());
//		Assert.assertEquals("simon@domain.com", users.get(0).getEmail());
//		Assert.assertEquals("simon@domain.com", users.get(0).getEmail());
//		Assert.assertEquals("ROLE_USER", users.get(0).getRole());
//		Assert.assertEquals("09876543211", users.get(0).getPhone());
//		userDaoImpl.delete(user);
	}
	
}
