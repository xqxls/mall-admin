package com.xqxls.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.mapper.UmsResourceDao;
import com.xqxls.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Objects;

/**
 * 后台资源表 服务实现类
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceDao, UmsResourceEntity> implements UmsResourceService {

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Autowired
    private UmsResourceDao umsResourceDao;

    @Override
    public int create(UmsResourceEntity umsResourceEntity) {
        umsResourceEntity.setCreateTime(new Date());
        return this.add(umsResourceEntity);
    }

    @Override
    public int update(Long id, UmsResourceEntity umsResourceEntity) {
        umsResourceEntity.setId(id);
        int count = this.update(umsResourceEntity);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    @Override
    public int delete(Long id) {
        int count = this.deleteById(id);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    @Override
    public PageInfo<UmsResourceEntity> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        Example example = new Example(UmsResourceEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if(Objects.nonNull(categoryId)){
            criteria.andEqualTo("categoryId", categoryId);
        }
        if(StrUtil.isNotEmpty(nameKeyword)){
            criteria.andLike("name", "%"+nameKeyword+"%");
        }
        if(StrUtil.isNotEmpty(urlKeyword)){
            criteria.andLike("url", "%"+urlKeyword+"%");
        }
        return new PageInfo<>(umsResourceDao.selectByExample(example));
    }
}
