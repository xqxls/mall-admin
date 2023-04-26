package com.xqxls.mall.service;

import com.github.pagehelper.PageInfo;
import com.xqxls.mall.dto.node.UmsMenuNode;
import com.xqxls.mall.entity.UmsMenuEntity;

import java.util.List;


/**
 * 后台菜单表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsMenuService extends IService<UmsMenuEntity>{

    /**
     * 新增菜单
     * @param umsMenuEntity
     * @return
     */
    int create(UmsMenuEntity umsMenuEntity);

    /**
     * 跟新菜单
     * @param id
     * @param umsMenuEntity
     * @return
     */
    int update(Long id, UmsMenuEntity umsMenuEntity);

    /**
     * 分页查询菜单
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    PageInfo<UmsMenuEntity> list(Long parentId, Integer page, Integer size);

    /**
     * 树形结构返回所有菜单列表
     * @return
     */
    List<UmsMenuNode> treeList();

    /**
     * 修改菜单显示状态
     * @param id
     * @param hidden
     * @return
     */
    int updateHidden(Long id, Integer hidden);
}
