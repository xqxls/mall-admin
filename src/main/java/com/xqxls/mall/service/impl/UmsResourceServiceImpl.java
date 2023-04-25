package com.xqxls.mall.service.impl;

import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.mapper.UmsResourceDao;
import com.xqxls.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台资源表 服务实现类
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceDao, UmsResourceEntity> implements UmsResourceService {

    @Autowired
    private UmsResourceDao umsResourceDao;

    @Override
    public List<UmsResourceEntity> findAll() {
        return umsResourceDao.selectAll();
    }
}
