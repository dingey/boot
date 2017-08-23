package com.d.mapper.test1;

import com.d.entity.User;
import com.d.enums.UserSexEnum;
import com.d.util.SqlProvider;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface User1Mapper {

	List<User> getAll();

	@SelectProvider(type = SqlProvider.class, method = "get")
	@Results({ @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class) })
	User getOne(User u);

	@InsertProvider(type = SqlProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(User user);

	@UpdateProvider(type = SqlProvider.class, method = "update")
	int update(User user);

	@DeleteProvider(type = SqlProvider.class, method = "delete")
	int delete(Long id);
}