package com.xqxls.mall.service;

import java.util.List;

/**
 * 通用Service
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public interface IService<T> {

    /**
     * 根据主键查询数据
     *
     * @param id 主键id
     * @return 查询的数据
     */
    T findById(Long id);

    /**
     * 插入数据库记录
     *
     * @param entity 数据
     * @return 成功插入条数
     */
    int add(T entity);


    /**
     * 根据主键更新数据
     *
     * @param entity 数据
     * @return 成功更新条数
     */
    int update(T entity);


    /**
     * 根据主键删除数据
     *
     * @param id 主键
     * @return 成功删除条数
     */
    int deleteById(Long id);

    /**
     * 查询所有数据
     * @return 所有数据
     */
    List<T> findAll();

    /**
     * 批量插入数据
     * @param list 插入数据列表
     * @return 成功插入条数
     */
    int insertBatch(List<T> list);

    /**
     * 根据id批量删除
     * @param idList 主键ID列表
     * @return 成功删除条数
     */
    int deleteByIdList(List<Long> idList);

}
