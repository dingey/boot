package com.d.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

@SuppressWarnings({ "rawtypes" })
public interface AbstractService<T extends BaseEntity<T>, S extends AbstractService> {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 一条记录
     */
    T get(Integer id);

    /**
     * 根据主键查询并缓存结果
     *
     * @param id 主键
     * @return 一条记录
     */
    T getCache(Integer id);

    /**
     * 查询一条记录,和entity字段值不为空相等的一条记录，大于1条报错
     *
     * @param entity 查询对象
     * @return 一条记录
     */
    T get(T entity);

    /**
     * 查询和entity字段值不为空相等的多条记录
     *
     * @param entity 查询对象
     * @return 多条记录
     */
    List<T> list(T entity);

    /**
     * 汇总和entity字段值不为空相等的记录数
     *
     * @param entity 查询对象
     * @return 记录数
     */
    Integer count(T entity);

    /**
     * 分页查询
     *
     * @param entity   查询对象
     * @param pageNum  页码
     * @param pageSize 大小
     * @return 分页数据
     */
    PageInfo<T> page(T entity, int pageNum, int pageSize);

    /**
     * 查询所有
     *
     * @return 所有记录
     */
    List<T> listAll();

    /**
     * 分页查询
     *
     * @param pageNum  页码
     * @param pageSize 大小
     * @return 分页数据
     */
    PageInfo<T> pageAll(int pageNum, int pageSize);

    /**
     * 根据主键批量查询
     *
     * @param ids 主键
     * @return 多条记录
     */
    List<T> listByIds(Iterable<Integer> ids);

    /**
     * 根据主键从缓存查询
     *
     * @param ids 主键
     * @return 多条记录
     */
    List<T> listByIdsCache(Iterable<Integer> ids);

    /**
     * 查询总记录数
     *
     * @return 总记录数
     */
    int countAll();

    /**
     * 插入或修改记录（主键大于0或newRecord=false修改记录，其他插入记录）。
     *
     * @param entity 实体
     * @return 影响行数
     */
    int save(T entity);

    /**
     * 插入或修改记录，并更新缓存（id>0或newRecord=false修改记录，其他插入记录）。
     *
     * @param entity 实体
     * @return 影响的行数
     */
    int saveCache(T entity);

    /**
     * 删除记录
     *
     * @param id 主键
     * @return 影响的行数
     */
    int delete(Integer id);

    /**
     * 删除记录并清空缓存
     *
     * @param id 主键
     * @return 影响的行数
     */
    int deleteCache(Integer id);
}
