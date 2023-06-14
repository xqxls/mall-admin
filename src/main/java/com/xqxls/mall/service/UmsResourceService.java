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
     * @param umsResourceEntity 资源实体
     * @return 成功新增条数
     */
    int create(UmsResourceEntity umsResourceEntity);

    /**
     * 编辑
     * @param id 资源ID
     * @param umsResourceEntity 资源实体
     * @return 成功编辑条数
     */
    int update(Long id, UmsResourceEntity umsResourceEntity);

    /**
     * 删除
     * @param id 资源ID
     * @return 成功删除条数
     */
    int delete(Long id);

    /**
     * 分页查询
     * @param categoryId 资源分类ID
     * @param nameKeyword 资源名称搜索关键词
     * @param urlKeyword 资源URL搜索关键词
     * @param page 当前页
     * @param size 每一页记录数
     * @return 资源分页信息
     */
    PageInfo<UmsResourceEntity> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer size);
}
