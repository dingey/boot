package com.d.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.d.base.BaseMapper;
import com.d.entity.Permission;

public interface PermissionMapper extends BaseMapper<Permission> {
	@Select("select * from permission where del_flag=0 and id in (select permission_id from role_permission where role_id=#{roleId})")
	List<Permission> listByRoleId(Integer roleId);

	@Select("select * from permission where del_flag=0 order by parent_id asc,`sequence` asc")
	List<Permission> listNormal();

	@Update("update permission set `sequence`=`sequence`-1 where parent_id=#{parentId} and `sequence`>#{sequence} and del_flag=0")
	Integer updateOriginal(@Param("parentId") int parentId, @Param("sequence") int sequence);

	@Update("update permission set `sequence`=`sequence`+1 where parent_id=#{parentId} and `sequence`>=#{sequence} and del_flag=0")
	Integer updateTarget(@Param("parentId") int parentId, @Param("sequence") int sequence);

	@Select("select count(0) from permission where parent_id=#{parentId} and del_flag=0")
	Integer countByParentId(int parentId);
	
	@Select("SELECT * FROM permission WHERE id in(SELECT permission_id from role_permission WHERE role_id=#{roleId} AND del_flag=0) AND del_flag=0 ORDER BY parent_id ASC,sequence ASC")
	List<Permission> getByRoleId(int roleId);
}