package com.d;

import com.d.entity.Man;
import com.d.entity.User;
import com.d.util.JsonUtil;
import com.di.jdbc.mapper.JdbcMapper;
import com.di.jdbc.mapper.JdbcFactory;
import com.di.jdbc.mapper.core.Pager;
import com.di.jdbc.mapper.core.impl.DefaultDataSource;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("unused")
public class JdbcTest {
	static DefaultDataSource dataSource;
	static JdbcFactory jdbcFactory;

	@BeforeClass
	public static void setenv() {
//		dataSource = DefaultDataSource.build("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/boot?useUnicode=true&useSSL=true&characterEncoding=utf-8&serverTimezone=UTC",
//				"root", "root", 5, 5);
//		jdbcFactory = JdbcFactory.build(dataSource);
		
		DataSource dataSource=DefaultDataSource.build("jdbc:mysql://localhost:3306/sys", "root", "root");
		JdbcMapper mapper = JdbcMapper.build(dataSource);
	}

	@Test
	public void test() {
		JdbcMapper mapper = jdbcFactory.getMapper();
		// List<Map<String, ?>> list = mapper.list("select * from log");
		// for (Map<String, ?> m : list) {
		// Object o = m.get("id");
		// System.out.println(o);
		// }
		// list = mapper.list("select * from user");
		// list = mapper.list("select * from role");
		// list = mapper.list("select * from permission");
		// list = mapper.list("select * from project");
		// list = mapper.list("select * from role_permission");
		// list = mapper.list("select * from user_role");
		// User u1 = mapper.get("select * from user", User.class);
		// System.out.println("user" + JsonUtil.singleton().toJson(u1));
		// Map<?, ?> map1 = mapper.get("select * from user", Map.class);
		// System.out.println("map" + JsonUtil.singleton().toJson(map1));
		//
		// User u2 = mapper.get("select * from user", null, User.class);
		// System.out.println("user" + JsonUtil.singleton().toJson(u2));
		// Map<?, ?> map2 = mapper.get("select * from user", null, Map.class);
		// System.out.println("map" + JsonUtil.singleton().toJson(map2));

		// List<User> u3 = mapper.list("select * from user", User.class);
		// System.out.println("user" + u3.size());
		// List<Map> map3 = mapper.list("select * from user", Map.class);
		// System.out.println("map" + map3.size());

		// List<User> u4 = mapper.list("select * from user where id>?", new Object[] { 1
		// }, User.class);
		// System.out.println("user" + u4.size());
		// List<Map> map4 = mapper.list("select * from user where id>?", new Object[] {
		// 1 }, Map.class);
		// System.out.println("map" + map4.size());
		// Map<Object, Object> map5 = mapper.listToMap("select id,name from user");
		// System.out.println("listToMap" + map5);
		// Pager<Man> page1 = mapper.page("select * from man where id>?", new Object[] {
		// 5 }, 2, 5, Man.class);
		// System.out.println("page->total:" + page1.getTotal() + " size:" +
		// page1.getList().size() + " list:" +
		// JsonUtil.singleton().toJson(page1.getList()));
		// Pager<Map> p2 = mapper.page("select id,name from man", null, 1, 5);
		// System.out.println(JsonUtil.singleton().toJson(p2));
		// Man m1 = mapper.getByNamedQuery("get", new Object[] { 1 }, Man.class);
		// System.out.println(JsonUtil.singleton().toJson(m1));
		// Man m1 = new Man();
		// m1.setId(25);
		// m1.setAge(45);
		// mapper.updateSelective(m1);
	}
}
