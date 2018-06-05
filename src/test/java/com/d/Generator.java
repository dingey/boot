package com.d;

import org.junit.Test;

import com.d.base.BaseEntity;
import com.di.kit.MvcGenerater;
import com.di.kit.MvcGenerater.PersistenceEnum;

public class Generator {
	String url = "jdbc:mysql://localhost:3306/boot?useUnicode=true&useSSL=true&characterEncoding=utf-8&serverTimezone=UTC";
	String username = "root";
	String password = "root";
	@Test
	public void test() {
		MvcGenerater g = new MvcGenerater(url, username, password);
		g.setEntityBaseClass(BaseEntity.class);
		g.setTables("pay_info");
		g.setPersistence(PersistenceEnum.MYBATIS);
		g.createEntity("com.d.entity");
		g.createMapper("com.d.mapper");
	}
}
