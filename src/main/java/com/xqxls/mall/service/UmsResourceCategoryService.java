package com.xqxls.mall.service;

import com.xqxls.mall.entity.UmsResourceCategoryEntity;

/**
 * 资源分类表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategoryEntity>{

    /**
     * 新增资源分类
     * @param umsResourceCategory 资源分类实体
     * @return 成功新增条数
     */
    int create(UmsResourceCategoryEntity umsResourceCategory);
}
