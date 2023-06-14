package com.xqxls.mall.service;

import com.github.pagehelper.PageInfo;
import com.xqxls.mall.entity.UmsMenuEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.entity.UmsRoleEntity;

import java.util.List;

/**
 * 后台用户角色表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsRoleService extends IService<UmsRoleEntity>{

    /**
     * 获取用户对应菜单列表
     * @param adminId 用户ID
     * @return 菜单列表
     */
    List<UmsMenuEntity> getMenuList(Long adminId);

    /**
     * 新增
     * @param umsRoleEntity 角色实体
     * @return 新增成功条数
     */
    int create(UmsRoleEntity umsRoleEntity);

    /**
     * 批量删除
     * @param ids 角色ID列表
     */
    void deleteByIds(List<Long> ids);

    /**
     * 分页查询
     * @param keyword 搜索关键词
     * @param page 当前页
     * @param size 每一页记录数
     * @return 角色分页信息
     */
    PageInfo<UmsRoleEntity> list(String keyword, Integer page, Integer size);

    /**
     * 查指定角色对应的菜单
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<UmsMenuEntity> listMenu(Long roleId);

    /**
     * 查指定角色对应的资源
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<UmsResourceEntity> listResource(Long roleId);

    /**
     * 分配菜单
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 成功分配菜单数量
     */
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 分配资源
     * @param roleId 角色ID
     * @param resourceIds 资源ID列表
     * @return 成功分配资源数量
     */
    int allocResource(Long roleId, List<Long> resourceIds);
}
