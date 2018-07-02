package com.d.base;

import com.di.kit.SqlProvider;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings({"unchecked", "unused"})
public abstract class AbstractServiceImpl<D extends BaseMapper<T>, T extends BaseEntity<T>, S extends AbstractService> implements AbstractService<T, S> {
    public Logger logger = LoggerFactory.getLogger(AbstractServiceImpl.class);
    @Autowired
    protected D mapper;
    private Class<T> entityClass;
    private Field entityId;

    @Override
    public T get(Integer id) {
        T t = newEntity();
        if (t != null) {
            t.setId(id);
        }
        return this.get(t);
    }

    @Cacheable(value = "cache", key = "#root.targetClass.name+#id")
    @Override
    public T getCache(Integer id) {
        logger.info("查询数据库【{}】", id);
        return get(id);
    }

    public T get(T t) {
        return mapper.get(t);
    }

    @Override
    public List<T> list(T entity) {
        return mapper.list(entity);
    }

    @Override
    public Integer count(T entity) {
        return mapper.count(entity);
    }

    @Override
    public PageInfo<T> page(T entity, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> datas = list(entity);
        return new PageInfo<>(datas);
    }

    @Override
    public List<T> listAll() {
        return mapper.listAll(getEntityClass());
    }

    @Override
    public PageInfo<T> pageAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> datas = listAll();
        return new PageInfo<>(datas);
    }

    @Override
    public List<T> listByIds(Iterable<Integer> ids) {
        return mapper.listByIds(getEntityClass(), ids);
    }

    @Override
    public int countAll() {
        return mapper.countAll(getEntityClass());
    }

    @Override
    public int save(T entity) {
        if (entity.isNewRecord()) {
            return mapper.insertSelective(entity);
        } else {
            return mapper.updateSelective(entity);
        }
    }

    @CachePut(value = "cache", key = "#root.targetClass.name+#entity.id", condition = "#entity.id>0")
    @Override
    public int saveCache(T entity) {
        return save(entity);
    }

    @Override
    public int delete(Integer id) {
        T t = newEntity();
        if (t != null) {
            t.setId(id);
        }
        return mapper.delete(t);
    }

    @CacheEvict(value = "cache", key = "#root.targetClass.name+#id")
    @Override
    public int deleteCache(Integer id) {
        return delete(id);
    }

    @Override
    public S proxy() {
        return (S) AopContext.currentProxy();
    }

    private T newEntity() {
        try {
            return this.getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("实例化失败", e);
        }
        return null;
    }

    private Class<T> getEntityClass() {
        if (this.entityClass == null) {
            this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return entityClass;
    }

    private Field getEntityId() {
        if (entityId == null) {
            entityId = SqlProvider.id(getEntityClass());
        }
        return entityId;
    }
}
