package com.d.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.d.ApplicationTests;
import com.d.entity.User;
import com.d.mapper.test1.User1Mapper;

/**
 * @author d
 */
public class User1MapperTest extends ApplicationTests {
	@Autowired
	User1Mapper user1Mapper;

	@Override
	public void test() {
		User u = new User();
		u.setId(1L);
		u=user1Mapper.get(u);
//		System.out.println(u);
//		user1Mapper.getAll();
		User user = user1Mapper.getById(1);
	}
}
