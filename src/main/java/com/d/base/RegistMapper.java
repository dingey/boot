package com.d.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author di
 */
public class RegistMapper {
	private final SqlSessionFactory sqlSessionFactory;

	public RegistMapper(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.regist();
	}

	public void regist() {
		Configuration configuration = sqlSessionFactory.getConfiguration();
		for (Class<?> mapper : configuration.getMapperRegistry().getMappers()) {
			MappedStatement ms = configuration.getMappedStatement(mapper.getName() + ".get");
			SqlSource sqlSource = new SqlSource() {
				@Override
				public BoundSql getBoundSql(Object parameterObject) {
					Type type = ((ParameterizedType) mapper.getGenericSuperclass()).getActualTypeArguments()[0];
					ParameterMapping parameterMapping = null;
					// ParameterMapping.Builder(configuration, "",
					// (Class<?>)type);
					return new BoundSql(configuration, "select * from ", Arrays.asList(parameterMapping),
							parameterObject);
				}
			};
			MappedStatement mappedStatement = new MappedStatement.Builder(configuration, mapper.getName() + ".getById",
					sqlSource, SqlCommandType.SELECT).build();
			configuration.addMappedStatement(mappedStatement);
		}
	}
}
