package com.d.mapper;

import com.d.base.BaseMapper;
import com.d.entity.User;
import com.d.enums.UserSexEnum;
import com.d.util.SqlProvider;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

	List<User> getAll();

	@SelectProvider(type = SqlProvider.class, method = "get")
	@Results({ @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class) })
	User getOne(User u);
	
	User getById(long id);
}