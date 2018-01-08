package com.d.dto;

import java.io.Serializable;
import java.util.List;

import com.d.entity.Role;
import com.d.entity.RolePermission;
import com.d.util.SqlProvider.Transient;

/**
 * @author d
 */
public class RoleDTO implements Serializable {
	private static final long serialVersionUID = -5876519607762775250L;
	private Role role;
	@Transient
	private List<RolePermission> permissions;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<RolePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<RolePermission> permissions) {
		this.permissions = permissions;
	}

}
