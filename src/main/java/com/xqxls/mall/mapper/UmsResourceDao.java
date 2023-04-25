package com.xqxls.mall.mapper;

import com.xqxls.mall.entity.UmsResourceEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
 * 后台资源表 Mapper 接口
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsResourceDao extends Mapper<UmsResourceEntity> {

    /**
     * 获取用户所有可访问资源
     */
    List<UmsResourceEntity> getResourceList(@Param("adminId") Long adminId);
}