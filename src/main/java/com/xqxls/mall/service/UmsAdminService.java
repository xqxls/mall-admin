package com.xqxls.mall.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.github.pagehelper.PageInfo;
import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.dto.UpdateAdminPasswordDto;
import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.entity.UmsRoleEntity;

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
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    SaTokenInfo login(String username, String password);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户实体
     */
    UmsAdminEntity getAdminByUsername(String username);

    /**
     * 根据用户id获取角色信息
     * @param adminId 用户ID
     * @return 角色列表
     */
    List<UmsRoleEntity> getRoleList(Long adminId);

    /**
     * 根据用户id获取资源信息
     * @param adminId 用户ID
     * @return 资源列表
     */
    List<UmsResourceEntity> getResourceList(Long adminId);

    /**
     * 获取缓存服务类
     * @return 缓存服务类
     */
    UmsAdminCacheService getCacheService();

    /**
     * 注册
     * @param umsAdminRegisterDto 注册dto
     * @return 用户实体
     */
    UmsAdminEntity register(UmsAdminRegisterDto umsAdminRegisterDto);

    /**
     * 刷新token
     * @return 刷新之后的token
     */
    SaTokenInfo refreshToken();

    /**
     * 通过凭证获取当前登录用户信息
     * @param saTokenInfo token信息
     * @return 用户信息
     */
    Map<String,Object> getAdminInfoByToken(SaTokenInfo saTokenInfo);

    /**
     * 根据用户名或昵称分页查询用户
     * @param keyword 搜索关键词
     * @param page 当前页
     * @param size 每一页记录数
     * @return 用户分页信息
     */
    PageInfo<UmsAdminEntity> list(String keyword, Integer page, Integer size);

    /**
     * 修改用户
     * @param id 用户ID
     * @param admin 用户实体
     * @return 修改成功条数
     */
    int update(Long id, UmsAdminEntity admin);

    /**
     * 修改密码
     * @param updateAdminPasswordDto 修改密码dto
     * @return 修改成功条数
     */
    int updatePassword(UpdateAdminPasswordDto updateAdminPasswordDto);

    /**
     * 删除用户
     * @param id 用户ID
     * @return 成功删除条数
     */
    int delete(Long id);

    /**
     * 给用户分配角色
     * @param adminId 用户ID
     * @param roleIds 角色ID列表
     * @return 成功分配角色数量
     */
    int allocateRole(Long adminId, List<Long> roleIds);

    /**
     * 根据token获取tokenMap
     * @param saTokenInfo token信息
     * @return tokenMap
     */
    Map<String, String> getTokenMap(SaTokenInfo saTokenInfo);


}
