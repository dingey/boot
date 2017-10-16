package com.d.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.d.util.SqlProvider;

/**
 * @author di
 */
public interface BaseMapper<T> {
	@InsertProvider(type = SqlProvider.class, method = "insert")
	@Options(useGeneratedKeys = true)
	int insert(T t);

	@InsertProvider(type = SqlProvider.class, method = "insertSelective")
	@Options(useGeneratedKeys = true)
	int insertSelective(T t);

	@UpdateProvider(type = SqlProvider.class, method = "update")
	int update(T t);

	@UpdateProvider(type = SqlProvider.class, method = "updateSelective")
	int updateSelective(T t);

	@UpdateProvider(type = SqlProvider.class, method = "deleteMark")
	int delete(T t);

	@SelectProvider(type = SqlProvider.class, method = "get")
	T get(T t);

	@SelectProvider(type = SqlProvider.class, method = "findAll")
	List<T> findAll(T t);

	@Select("${sql}")
	List<T> findBySql(@Param("sql") String sql);

	@SelectProvider(type = SqlProvider.class, method = "countAll")
	int countAll(T t);

	@Select("${sql}")
	int countBySql(@Param("sql") String sql);
	
	T getById(Serializable id);
}
