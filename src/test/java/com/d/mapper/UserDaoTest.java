package com.d.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import com.d.ApplicationTests;
import com.d.dao.UserDao;
import com.d.entity.UserDto;

/**
 * @author d
 */
public class UserDaoTest extends ApplicationTests {
	@Autowired
	UserDao userDao;

	@Override
	public void test() {
		get();
		// insert();
	}

	public void get() {
		UserDto user = userDao.findOne(1);
		System.out.println(user.toString());
	}

}
