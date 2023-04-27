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

    int create(UmsRoleEntity umsRoleEntity);

    void deleteByIds(List<Long> ids);

    PageInfo<UmsRoleEntity> list(String keyword, Integer page, Integer size);

    List<UmsMenuEntity> listMenu(Long roleId);

    List<UmsResourceEntity> listResource(Long roleId);

    int allocMenu(Long roleId, List<Long> menuIds);

    int allocResource(Long roleId, List<Long> resourceIds);
}
