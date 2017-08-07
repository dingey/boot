package com.d.base;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
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

	@UpdateProvider(type = SqlProvider.class, method = "update")
	int update(T t);

	@UpdateProvider(type = SqlProvider.class, method = "delete")
	int delete(T t);

	@SelectProvider(type = SqlProvider.class, method = "get")
	T get(T t);

	@SelectProvider(type = SqlProvider.class, method = "findList")
	List<T> findList(T t);
}
