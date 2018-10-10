package com.d.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import com.d.ApplicationTests;
import com.d.entity.User;
import com.d.mapper.UserMapper;
import com.di.kit.JsonUtil;

/**
 * @author d
 */
public class UserMapperTest extends ApplicationTests {
	@Autowired
	UserMapper userMapper;

	@Override
	public void test() {
		// get();
		// insert();
		getByid();
	}

	public void get() {
		User u = new User();
		u.setId(1);
		User user = userMapper.get(u);
		System.out.println(user.toString());
	}

	public void getByid() {
		User u=new User();
		u.setId(1);
		userMapper.get(u);
	}

	public void insert() {
		User u = new User();
		u.setUsername("test");
		u.setPassword("test");
		// u.setNickName("test");
		// u.setUserSex(UserSexEnum.MAN);
		// u.setCreate(new Date());
		System.out.println(JsonUtil.toJson(u));
		userMapper.insertSelective(u);
		System.out.println(JsonUtil.toJson(u));
	}
}
