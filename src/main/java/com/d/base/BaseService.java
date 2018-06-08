package com.d.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.di.kit.SqlProvider;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author di
 */
public abstract class BaseService<D extends BaseMapper<T>, T extends BaseEntity<T>> {
	public Logger logger = LoggerFactory.getLogger(BaseService.class);
	@Autowired
	protected D mapper;
	private Class<T> entityClass;
	private Field entityId;

	public T get(Integer id) {
		T t = newEntity();
		t.setId(id);
		return this.get(t);
	}

	public T get(T t) {
		return mapper.get(t);
	}

	public List<T> listAll() {
		return mapper.listAll(getEntityClass());
	}

	public PageInfo<T> pageAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<T> datas = listAll();
		return new PageInfo<>(datas);
	}

	public List<T> listByIds(Iterable<Integer> ids) {
		return mapper.listByIds(getEntityClass(), ids);
	}

	public int countAll() {
		return mapper.countAll(getEntityClass());
	}

	public int save(T entity) {
		if (entity.isNewRecord()) {
			return mapper.insertSelective(entity);
		} else {
			return mapper.updateSelective(entity);
		}
	}

	public int delete(Integer id) {
		T t = newEntity();
		t.setId(id);
		return mapper.delete(t);
	}

	public int delete(T entity) {
		return mapper.delete(entity);
	}

	private T newEntity() {
		try {
			return this.getEntityClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("实例化失败", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		if (this.entityClass == null) {
			this.entityClass = (Class<T>) ((ParameterizedType) this.getClass()
					.getGenericSuperclass()).getActualTypeArguments()[1];
		}
		return entityClass;
	}

	@SuppressWarnings("unused")
	private Field getEntityId() {
		if (entityId == null) {
			entityId = SqlProvider.id(getEntityClass());
		}
		return entityId;
	}
}
