package com.d.enums;

/**
 * @author d
 */
public enum PermissionTypeEnum {
	NONE(0, "无"), NAVIGATION(1, "头部导航"), MENU(2, "一级菜单"), MENU_ITEM(3, "子菜单");
	private Integer value;
	private String name;

	PermissionTypeEnum(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public String getName(Integer value) {
		for (PermissionTypeEnum pte : values()) {
			if (pte.getValue().equals(value)) {
				return pte.getName();
			}
		}
		return NONE.getName();
	}

}
