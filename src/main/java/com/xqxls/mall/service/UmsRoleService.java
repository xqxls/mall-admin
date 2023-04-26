package com.xqxls.mall.service;

import com.xqxls.mall.entity.UmsMenuEntity;
import com.xqxls.mall.entity.UmsRoleEntity;

import java.util.List;

/**
 * 后台用户角色表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsRoleService extends IService<UmsRoleEntity>{

    List<UmsMenuEntity> getMenuList(Long adminId);
}
