package com.d.mapper.test2;

import java.util.List;

import com.d.entity.User;
import com.d.enums.UserSexEnum;

import org.apache.ibatis.annotations.*;

public interface User2Mapper {


	@Select("SELECT * FROM user")
	@Results({
			@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
			@Result(property = "nickName", column = "nick_name")
	})
	List<User> getAll();

	User getOne(Long id);

	@Insert("INSERT INTO user(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(User user);

	@Update("UPDATE user SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(User user);

	@Delete("DELETE FROM user WHERE id =#{id}")
	void delete(Long id);

}