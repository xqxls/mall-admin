package com.xqxls.mall.mapper;

import com.xqxls.mall.base.TkBaseMapper;
import com.xqxls.mall.entity.UmsAdminEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 后台用户表 Mapper 接口
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Component
public interface UmsAdminDao extends TkBaseMapper<UmsAdminEntity> {

    /**
     * 通过资源ID获取用户ID列表
     * @param resourceId 资源ID
     * @return 用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}