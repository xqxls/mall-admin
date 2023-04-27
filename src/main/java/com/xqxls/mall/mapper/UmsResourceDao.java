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
     */
    List<UmsResourceEntity> getResourceList(@Param("adminId") Long adminId);

    List<UmsResourceEntity> getResourceListByRoleId(@Param("roleId") Long roleId);
}