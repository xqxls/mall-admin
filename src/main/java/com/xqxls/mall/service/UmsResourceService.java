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

    /**
     * 新增
     * @param umsResourceEntity
     * @return
     */
    int create(UmsResourceEntity umsResourceEntity);

    /**
     * 编辑
     * @param id
     * @param umsResourceEntity
     * @return
     */
    int update(Long id, UmsResourceEntity umsResourceEntity);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 分页查询
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param page
     * @param size
     * @return
     */
    PageInfo<UmsResourceEntity> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer size);
}
