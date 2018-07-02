package com.d.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

@SuppressWarnings("unused")
public interface AbstractService<T extends BaseEntity<T>, S extends AbstractService> {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 实体
     */
    T get(Integer id);

    /**
     * 根据主键查询并缓存结果
     *
     * @param id 主键
     * @return 实体
     */
    T getCache(Integer id);

    /**
     * 查询
     *
     * @param entity 查询对象
     * @return 和entity值不为空相等的所有记录
     */
    List<T> list(T entity);

    /**
     * 汇总
     *
     * @param entity 查询对象
     * @return 和entity值不为空相等的记录总数
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
     * @return 记录
     */
    List<T> listByIds(Iterable<Integer> ids);

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

    /**
     * 获取自身的代理对象,内部调用时解决缓存、事物失效问题。
     *
     * @return 代理对象
     */
    S proxy();
}
