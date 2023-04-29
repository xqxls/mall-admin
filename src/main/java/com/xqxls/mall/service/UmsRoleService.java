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
     * @param adminId
     * @return
     */
    List<UmsMenuEntity> getMenuList(Long adminId);

    /**
     * 新增
     * @param umsRoleEntity
     * @return
     */
    int create(UmsRoleEntity umsRoleEntity);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 分页查询
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    PageInfo<UmsRoleEntity> list(String keyword, Integer page, Integer size);

    /**
     * 查指定角色对应的菜单
     * @param roleId
     * @return
     */
    List<UmsMenuEntity> listMenu(Long roleId);

    /**
     * 查指定角色对应的资源
     * @param roleId
     * @return
     */
    List<UmsResourceEntity> listResource(Long roleId);

    /**
     * 分配菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 分配资源
     * @param roleId
     * @param resourceIds
     * @return
     */
    int allocResource(Long roleId, List<Long> resourceIds);
}
