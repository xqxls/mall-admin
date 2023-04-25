package com.xqxls.mall.service;


import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 后台用户表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsAdminService {

    String login(String username, String password);

    UmsAdminEntity getAdminByUsername(String username);

    List<UmsResourceEntity> getResourceList(Long adminId);

    UserDetails loadUserByUsername(String username);
}
