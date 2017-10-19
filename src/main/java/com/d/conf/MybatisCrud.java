package com.d.conf;

import java.util.Arrays;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;

import com.d.entity.User;
import com.di.kit.PathUtil;

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

	public void buildCrud() {
		Configuration conf = this.sqlSessionFactory.getConfiguration();
		LanguageDriver langDriver = conf.getDefaultScriptingLanguageInstance();
		String databaseId = conf.getDatabaseId();
		SqlSource sqlSource = langDriver.createSqlSource(conf, "select * from user where id=#{id}", long.class);
		String id = "com.d.mapper.test1.User1Mapper.getById";

		ResultMapping rmp = new ResultMapping.Builder(conf, "id").nestedQueryId("").column("id").build();
		ResultMap rm = new ResultMap.Builder(conf, id, User.class, Arrays.asList(rmp), true).build();

		MappedStatement ms = new MappedStatement.Builder(conf, id, sqlSource, SqlCommandType.SELECT)
				.resultMaps(Arrays.asList(rm)).resultSetType(ResultSetType.FORWARD_ONLY).build();
		conf.addMappedStatement(ms);

		// String resource = PathUtil.getClassPath() +
		// "mybatis/mybatis-config.xml";
		// MapperBuilderAssistant mapperBuilderAssistant = new
		// MapperBuilderAssistant(conf, resource);
		// mapperBuilderAssistant.addMappedStatement(id, sqlSource,
		// StatementType.PREPARED, SqlCommandType.SELECT, 1, 1,
		// "id", Long.class, null, User.class, null, false, false, false, null,
		// "id", "id", databaseId, langDriver,
		// null);
	}

}
