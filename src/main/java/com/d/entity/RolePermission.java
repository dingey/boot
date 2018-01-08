package com.d.entity;

import com.d.base.BaseEntity;

/**
 * @author d
 */
public class RolePermission extends BaseEntity<RolePermission> {
	private static final long serialVersionUID = -3005731236868031863L;
	private Integer roleId;
	private Integer permissionId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
}
