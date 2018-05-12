package com.d.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.d.base.BaseMapper;
import com.d.entity.Role;

public interface RoleMapper extends BaseMapper<Role> {
	@Select("select * from role where id in (select role_id from user_role where user_id=#{user_id} and  del_flag=0)")
	List<Role> listByUserId(Integer userId);
	
	@Select("<script>select * from role where del_flag=0 <if test=\"name !=null and name!=''\"> and `name` like CONCAT('%',#{name},'%')</if></script>")
	List<Role> listByName(@Param("name")String name);
}