package com.d.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.d.base.BaseMapper;
import com.d.entity.UserRole;

public interface UserRoleMepper extends BaseMapper<UserRole> {
	@Select("select * from user_role where user_id=#{userId} and del_flag=0")
	List<UserRole> listByUserId(Integer userId);
}
