package com.xqxls.mall.service;

import com.github.pagehelper.PageInfo;
import com.xqxls.mall.entity.UmsResourceEntity;


/**
 * 后台资源表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsResourceService extends IService<UmsResourceEntity>{

    int create(UmsResourceEntity umsResourceEntity);

    int update(Long id, UmsResourceEntity umsResourceEntity);

    int delete(Long id);

    PageInfo<UmsResourceEntity> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer size);
}
