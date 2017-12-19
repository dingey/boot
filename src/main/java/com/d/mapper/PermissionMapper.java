package com.d.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.d.base.BaseMapper;
import com.d.entity.Permission;

public interface PermissionMapper extends BaseMapper<Permission> {
	@Select("select * from permission where id in (select permission_id from role_permission where role_id=#{roleId})")
	List<Permission> listByRoleId(Integer roleId);
}