package com.d.base;

import com.github.pagehelper.PageInfo;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@SuppressWarnings("unused")
public interface AbstractService<T extends BaseEntity<T>, S extends AbstractService> {
    //@Cacheable(value = "cache", key = "#root.targetClass.name+#entity.id")
    T get(Integer id);

    List<T> listAll();

    PageInfo<T> pageAll(int pageNum, int pageSize);

    List<T> listByIds(Iterable<Integer> ids);

    int countAll();

    //@CacheEvict(value = "cache", key = "#root.targetClass.name+#entity.id")
    int save(T entity);

    //@CacheEvict(value = "cache", key = "#root.targetClass.name+#id")
    int delete(Integer id);

    S proxy();
}
