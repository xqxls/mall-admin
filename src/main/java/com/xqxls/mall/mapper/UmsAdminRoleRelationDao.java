package com.xqxls.mall.mapper;

import com.xqxls.mall.base.TkBaseMapper;
import com.xqxls.mall.entity.UmsAdminRoleRelationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 后台用户和角色关系表 Mapper 接口
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Component
public interface UmsAdminRoleRelationDao extends TkBaseMapper<UmsAdminRoleRelationEntity> {

    /**
     * 批量新增用户角色关系
     * @param list 用户角色关系
     * @return 新增成功条数
     */
    int addBatch(@Param("list") List<UmsAdminRoleRelationEntity> list);

    /**
     * 通过用户ID删除用户角色关系
     * @param adminId 用户ID
     * @return 删除成功条数
     */
    int delByAdminId(@Param("adminId") Long adminId);
}