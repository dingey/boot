package com.d.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.d.base.BaseMapper;
import com.d.entity.RolePermission;

/**
 * @author d
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
	@Select("select * from role_permission where del_flag=0 and role_id=#{roleId}")
	List<RolePermission> listByRoleId(Integer roleId);
}
