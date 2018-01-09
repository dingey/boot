package com.d.entity;

import com.d.base.BaseEntity;

public class Permission extends BaseEntity<Permission> {
	private static final long serialVersionUID = 7061149634638162707L;
	private String name;
	private String url;
	private String permission;
	private Integer parentId;
	private Integer sequence;
	private String icon;
	private Integer type;
	private Integer authc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAuthc() {
		return authc;
	}

	public void setAuthc(Integer authc) {
		this.authc = authc;
	}

}