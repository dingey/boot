package com.d.dto;

import java.util.List;

import com.d.entity.Permission;

/**
 * @author d
 */
public class PermissionDTO extends Permission {
	private static final long serialVersionUID = -2349223128534997383L;
	private List<PermissionDTO> children;

	public List<PermissionDTO> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionDTO> children) {
		this.children = children;
	}

}
