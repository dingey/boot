package com.d.conf;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;

import com.d.entity.User;

/**
 * @author d
 */
@SuppressWarnings("unused")
public class MybatisCrud {
	private SqlSessionFactory sqlSessionFactory;

	public MybatisCrud(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		try {
			this.buildCrud();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buildCrud() throws Exception {
		Configuration conf = this.sqlSessionFactory.getConfiguration();
		LanguageDriver langDriver = conf.getDefaultScriptingLanguageInstance();
		SqlSource sqlSource = langDriver.createSqlSource(conf, "select * from user where id=#{id}", long.class);
		String id = "com.d.mapper.test1.User1Mapper.getById";
		MappedStatement ms = new MappedStatement.Builder(conf, id, sqlSource, SqlCommandType.SELECT).build();
		conf.addMappedStatement(ms);
	}

}
