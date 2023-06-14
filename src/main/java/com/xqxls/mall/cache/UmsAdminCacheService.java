package com.xqxls.mall.cache;

import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import java.util.List;

/**
 * 后台用户缓存管理Service
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public interface UmsAdminCacheService {

    /**
     * 删除后台用户缓存
     * @param adminId 用户ID
     */
    void delAdmin(Long adminId);

    /**
     * 删除后台用户资源列表缓存
     * @param adminId 用户ID
     */
    void delResourceList(Long adminId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     * @param roleId 角色ID
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     * @param roleIds 角色ID列表
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     * @param resourceId 资源ID
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     * @param username 用户名
     * @return 用户实体
     */
    UmsAdminEntity getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     * @param admin 用户实体
     */
    void setAdmin(UmsAdminEntity admin);

    /**
     * 获取缓存后台用户资源列表
     * @param adminId 用户ID
     * @return 资源实体列表
     */
    List<UmsResourceEntity> getResourceList(Long adminId);

    /**
     * 设置后台用户资源列表
     * @param adminId 用户ID
     * @param resourceList 资源ID列表
     */
    void setResourceList(Long adminId, List<UmsResourceEntity> resourceList);
}
