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
     * @return
     */
    T findById(Long id);

    /**
     * 插入数据库记录
     *
     * @param entity 数据
     * @return
     */
    int add(T entity);


    /**
     * 根据主键更新数据
     *
     * @param entity 数据
     * @return
     */
    int update(T entity);


    /**
     * 根据主键删除数据
     *
     * @param id 主键
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询所有数据
     * @return
     */
    List<T> findAll();

    /**
     * 批量插入数据
     * @return
     */
    int insertBatch(List<T> list);

    /**
     * 根据id批量删除
     * @return
     */
    int deleteByIdList(List<Long> idList);

}
