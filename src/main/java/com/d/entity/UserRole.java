package com.d.entity;

import com.d.base.BaseEntity;

/**
 * @author d
 */
public class UserRole extends BaseEntity<UserRole> {
	private static final long serialVersionUID = 3113143000531567466L;
	private Integer userId;
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
