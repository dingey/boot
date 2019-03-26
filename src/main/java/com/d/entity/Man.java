package com.d.entity;

import com.d.base.BaseEntity;

public class Man extends BaseEntity<Man> {
	private static final long serialVersionUID = 5478404573506550506L;
	private String name;
	private Integer age;
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
