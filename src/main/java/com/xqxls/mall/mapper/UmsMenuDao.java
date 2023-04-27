package com.xqxls.mall.mapper;

import com.xqxls.mall.base.TkBaseMapper;
import com.xqxls.mall.entity.UmsMenuEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 后台菜单表 Mapper 接口
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Component
public interface UmsMenuDao extends TkBaseMapper<UmsMenuEntity> {

    /**
     * 根据后台用户ID获取菜单
     */
    List<UmsMenuEntity> getMenuList(@Param("adminId") Long adminId);

    List<UmsMenuEntity> getMenuListByRoleId(@Param("roleId") Long roleId);
}