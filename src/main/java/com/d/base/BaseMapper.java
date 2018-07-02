package com.d.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.di.kit.SqlProvider;

/**
 * @author di
 */
@SuppressWarnings("unused")
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

	@SelectProvider(type = SqlProvider.class, method = "getById")
	T getById(Class<T> t, Serializable id);

	@SelectProvider(type = SqlProvider.class, method = "list")
	List<T> list(T t);

	@SelectProvider(type = SqlProvider.class, method = "count")
	Integer count(T t);

	@SelectProvider(type = SqlProvider.class, method = "listAll")
	List<T> listAll(Class<T> t);

	@SelectProvider(type = SqlProvider.class, method = "countAll")
	int countAll(Class<T> t);

	@SelectProvider(type = SqlProvider.class, method = "listByIds")
	List<T> listByIds(Class<T> t, Iterable<Integer> ids);

	T getById(Serializable id);
}
