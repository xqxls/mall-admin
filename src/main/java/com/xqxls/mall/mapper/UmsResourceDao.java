package com.xqxls.mall.mapper;

import com.xqxls.mall.base.TkBaseMapper;
import com.xqxls.mall.entity.UmsResourceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 后台资源表 Mapper 接口
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Component
public interface UmsResourceDao extends TkBaseMapper<UmsResourceEntity> {

    /**
     * 获取用户所有可访问资源
     * @param adminId 用户ID
     * @return 资源列表
     */
    List<UmsResourceEntity> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取角色所有可访问资源
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<UmsResourceEntity> getResourceListByRoleId(@Param("roleId") Long roleId);
}