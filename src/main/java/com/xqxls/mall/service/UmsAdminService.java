package com.xqxls.mall.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.github.pagehelper.PageInfo;
import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.dto.UpdateAdminPasswordDto;
import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.entity.UmsRoleEntity;

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
    SaTokenInfo login(String username, String password);

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
     * @param
     * @return
     */
    SaTokenInfo refreshToken();

    /**
     * 通过凭证获取当前登录用户信息
     * @param
     * @return
     */
    Map<String,Object> getAdminInfoByToken(SaTokenInfo saTokenInfo);

    /**
     * 根据用户名或昵称分页查询用户
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    PageInfo<UmsAdminEntity> list(String keyword, Integer page, Integer size);

    /**
     * 修改用户
     * @param id
     * @param admin
     * @return
     */
    int update(Long id, UmsAdminEntity admin);

    /**
     * 修改密码
     * @param updateAdminPasswordDto
     * @return
     */
    int updatePassword(UpdateAdminPasswordDto updateAdminPasswordDto);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 给用户分配角色
     * @param adminId
     * @param roleIds
     * @return
     */
    int allocateRole(Long adminId, List<Long> roleIds);

    /**
     * 根据token获取tokenMap
     * @param saTokenInfo
     * @return
     */
    Map<String, String> getTokenMap(SaTokenInfo saTokenInfo);


}
