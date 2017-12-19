package com.d.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.d.base.BaseMapper;
import com.d.entity.Role;

public interface RoleMapper extends BaseMapper<Role> {
	@Select("select * from role where id in (select role_id from user_role where user_id=#{user_id})")
	List<Role> listByUserId(Integer userId);
}