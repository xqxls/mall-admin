package com.xqxls.mall.service.impl;

import com.xqxls.mall.base.TkBaseMapper;
import com.xqxls.mall.common.id.IdWorker;
import com.xqxls.mall.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 基础service实现类
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public class ServiceImpl<M extends TkBaseMapper<T>, T> implements IService<T> {

    @Autowired
    protected M mapper;

    @Override
    public T findById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int add(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int update(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }


}
