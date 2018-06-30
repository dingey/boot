package com.d.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

@SuppressWarnings("unused")
public interface AbstractService<T extends BaseEntity<T>, S extends AbstractService> {

    T get(Integer id);

    List<T> listAll();

    PageInfo<T> pageAll(int pageNum, int pageSize);

    List<T> listByIds(Iterable<Integer> ids);

    int countAll();

    int save(T entity);

    int delete(Integer id);

    S proxy();
}
