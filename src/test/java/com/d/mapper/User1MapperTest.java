package com.d.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.d.ApplicationTests;
import com.d.entity.User;
import com.d.mapper.test1.User1Mapper;
import com.di.kit.JsonUtil;

/**
 * @author d
 */
public class User1MapperTest extends ApplicationTests {
	@Autowired
	User1Mapper user1Mapper;

	@Override
	public void test() {
		// get();
		// insert();
		user1Mapper.getById(1);
	}

	public void get() {
		User u = new User();
		u.setId(1L);
		User user = user1Mapper.get(u);
		System.out.println(user.toString());
	}

	public void insert() {
		User u = new User();
		u.setUserName("test");
		u.setPassword("test");
		// u.setNickName("test");
		// u.setUserSex(UserSexEnum.MAN);
		// u.setCreate(new Date());
		System.out.println(JsonUtil.toJson(u));
		user1Mapper.insertSelective(u);
		System.out.println(JsonUtil.toJson(u));
	}
}
