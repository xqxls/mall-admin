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
     * @param umsMenuEntity 菜单实体
     * @return 成功新增条数
     */
    int create(UmsMenuEntity umsMenuEntity);

    /**
     * 更新菜单
     * @param id 菜单ID
     * @param umsMenuEntity 菜单实体
     * @return 成功更新条数
     */
    int update(Long id, UmsMenuEntity umsMenuEntity);

    /**
     * 分页查询菜单
     * @param parentId 父级ID
     * @param page 当前页
     * @param size 每一页记录数
     * @return 菜单分页信息
     */
    PageInfo<UmsMenuEntity> list(Long parentId, Integer page, Integer size);

    /**
     * 树形结构返回所有菜单列表
     * @return 菜单节点列表
     */
    List<UmsMenuNode> treeList();

    /**
     * 修改菜单显示状态
     * @param id 菜单ID
     * @param hidden 显示状态
     * @return 成功修改条数
     */
    int updateHidden(Long id, Integer hidden);

}
