package com.xqxls.mall.service;


import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.entity.UmsRoleEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 后台用户表 服务类接口
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
public interface UmsAdminService extends IService<UmsAdminEntity>{

    /**
     * 用户名密码登录
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    UmsAdminEntity getAdminByUsername(String username);

    /**
     * 根据用户id获取角色信息
     * @param adminId
     * @return
     */
    List<UmsRoleEntity> getRoleList(Long adminId);

    /**
     * 根据用户id获取资源信息
     * @param adminId
     * @return
     */
    List<UmsResourceEntity> getResourceList(Long adminId);

    /**
     * 根据用户名获取Security认证信息
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取缓存服务类
     * @return
     */
    UmsAdminCacheService getCacheService();

    /**
     * 注册
     * @param umsAdminRegisterDto
     * @return
     */
    UmsAdminEntity register(UmsAdminRegisterDto umsAdminRegisterDto);

    /**
     * 刷新token
     * @param oldToken
     * @return
     */
    String refreshToken(String oldToken);

    /**
     * 通过凭证获取当前登录用户信息
     * @param principal
     * @return
     */
    Map<String,Object> getAdminInfoByPrincipal(Principal principal);


}
