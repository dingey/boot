package com.d.entity;

import com.d.base.BaseEntity;

public class Role extends BaseEntity<Role> {
	private static final long serialVersionUID = 4909190970710572267L;
	private String name;
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}