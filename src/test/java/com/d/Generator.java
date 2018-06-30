package com.d;

import org.junit.Test;

import com.d.base.BaseEntity;
import com.di.kit.MvcGenerater;
import com.di.kit.JdbcMeta.Type;
import com.di.kit.MvcGenerater.PersistenceEnum;

public class Generator {
	String url = "jdbc:mysql://localhost:3306/boot?useUnicode=true&useSSL=true&characterEncoding=utf-8&serverTimezone=UTC";
	String username = "root";
	String password = "root";

	@Test
	public void test() {
		 //gen();
	}

	void gen() {
		MvcGenerater g = new MvcGenerater(url, username, password);
		g.setEntityBaseClass(BaseEntity.class);
		g.setTables("log");
		g.setPersistence(PersistenceEnum.MYBATIS);
		g.setBaseColumnList(true);
		g.setGenerateCrud(false);
		g.setSwaggerEntity(true);
		g.sqlTypeAdaptor(Type.SMALLINT, Integer.class);
		g.sqlTypeAdaptor(Type.TINYINT, int.class);
		g.createEntity("com.d.entity");
		g.createMapper("com.d.mapper");
		g.setBaseColumnList(true);
		g.createXml("mapper/");
	}
}
