package com.xqxls.mall.service;

import com.xqxls.mall.entity.UmsResourceEntity;

import java.util.List;


/**
 * 后台资源表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsResourceService extends IService<UmsResourceEntity>{

    List<UmsResourceEntity> findAll();
}
