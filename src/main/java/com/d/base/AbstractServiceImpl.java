package com.d.base;

import com.d.annotation.CacheMethod;
import com.di.kit.SqlProvider;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "unused", "rawtypes"})
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
        return mapper.get(t);
    }

    @Cacheable(value = "cache", key = "#root.targetClass.name+#id")
    @Override
    public T getCache(Integer id) {
        logger.info("查询数据库【{}】", id);
        T t = newEntity();
        if (t != null) {
            t.setId(id);
        }
        return mapper.get(t);
    }

    public T get(T t) {
        List<T> list = list(t);
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new RuntimeException("期望1条，但是返回了" + list.size() + "条记录。");
        }
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
    public List<T> listByIdsCache(Iterable<Integer> ids) {
        List<T> list = new ArrayList<>();
        for (Integer id : ids) {
            T t = (T) proxy().getCache(id);
            if (t != null) {
                list.add(t);
            }
        }
        return list;
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

    @CacheEvict(value = "cache", key = "#root.targetClass.name+#entity.id", condition = "#entity.id>0")
    @Override
    public int saveCache(T entity) {
        if (entity.isNewRecord()) {
            return mapper.insertSelective(entity);
        } else {
            return mapper.updateSelective(entity);
        }
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
        T t = newEntity();
        if (t != null) {
            t.setId(id);
        }
        return mapper.delete(t);
    }

    /**
     * 获取自身的代理对象,内部调用时解决缓存失效问题。
     *
     * @return 代理对象
     */
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
