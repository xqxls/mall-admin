package com.xqxls.mall.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.common.api.CommonResult;
import com.xqxls.mall.common.exception.Asserts;
import com.xqxls.mall.convert.mapstruct.UmsAdminMapper;
import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.dto.UpdateAdminPasswordDto;
import com.xqxls.mall.entity.*;
import com.xqxls.mall.mapper.*;
import com.xqxls.mall.service.UmsAdminService;
import com.xqxls.mall.service.UmsRoleService;
import com.xqxls.mall.util.SpringUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台用户表 服务实现类
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Service
@Slf4j
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminDao,UmsAdminEntity> implements UmsAdminService {

    @Autowired
    private UmsAdminDao umsAdminDao;

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsResourceDao umsResourceDao;

    @Autowired
    private UmsAdminLoginLogDao umsAdminLoginLogDao;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Autowired
    private UmsRoleService umsRoleService;

    @Override
    public SaTokenInfo login(String username, String password) {
        SaTokenInfo saTokenInfo = null;
        UmsAdminEntity umsAdminEntity = getAdminByUsername(username);
        if (umsAdminEntity == null) {
            return null;
        }
        if (!SaSecureUtil.md5(password).equals(umsAdminEntity.getPassword())) {
            return null;
        }
        // 密码校验成功后登录，一行代码实现登录
        StpUtil.login(umsAdminEntity.getId());
        insertLoginLog(username);
        // 获取当前登录用户Token信息
        saTokenInfo = StpUtil.getTokenInfo();
        return saTokenInfo;
    }

    private void insertLoginLog(String username) {
        UmsAdminEntity umsAdminEntity = getAdminByUsername(username);
        if(umsAdminEntity==null) return;
        UmsAdminLoginLogEntity umsAdminLoginLogEntity = new UmsAdminLoginLogEntity();
        umsAdminLoginLogEntity.setPrimaryId();
        umsAdminLoginLogEntity.setAdminId(umsAdminEntity.getId());
        umsAdminLoginLogEntity.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes!=null){
            HttpServletRequest request = attributes.getRequest();
            umsAdminLoginLogEntity.setIp(request.getRemoteAddr());
            umsAdminLoginLogEntity.setAddress(request.getRemoteHost()+":"+request.getRemotePort());
        }
        umsAdminLoginLogDao.insert(umsAdminLoginLogEntity);
    }


    @Override
    public UmsAdminEntity getAdminByUsername(String username) {
        UmsAdminEntity admin = this.getCacheService().getAdmin(username);
        if(admin!=null) return  admin;
        Example example = new Example(UmsAdminEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<UmsAdminEntity> umsAdminEntityList = umsAdminDao.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsAdminEntityList)&&umsAdminEntityList.size()>0) {
            admin = umsAdminEntityList.get(0);
            getCacheService().setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public List<UmsRoleEntity> getRoleList(Long adminId) {
        return umsRoleDao.getRoleList(adminId);
    }

    @Override
    public UmsAdminCacheService getCacheService() {
        return SpringUtil.getBean(UmsAdminCacheService.class);
    }

    @Override
    public UmsAdminEntity register(UmsAdminRegisterDto umsAdminRegisterDto) {
        UmsAdminEntity umsAdminEntity = UmsAdminMapper.INSTANCE.umsAdminRegisterToEntity(umsAdminRegisterDto);
        umsAdminEntity.setPrimaryId();
        umsAdminEntity.setCreateTime(new Date());
        umsAdminEntity.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminEntity queryEntity = this.getAdminByUsername(umsAdminEntity.getUsername());
        if (Objects.nonNull(queryEntity)) {
            Asserts.fail("用户名已存在");
        }
        //将密码进行加密操作
        String encodePassword = SaSecureUtil.md5(umsAdminEntity.getPassword());
        umsAdminEntity.setPassword(encodePassword);
        this.add(umsAdminEntity);
        return null;
    }

    @Override
    public SaTokenInfo refreshToken() {
        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
        // 如果token已经过期，抛出异常
        StpUtil.checkActivityTimeout();
        // 续签当前Token：(将 [最后操作时间] 更新为当前时间戳)
        StpUtil.updateLastActivityToNow();
        return saTokenInfo;
    }

    @Override
    public Map<String, String> getTokenMap(SaTokenInfo saTokenInfo) {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", saTokenInfo.getTokenValue());
        tokenMap.put("tokenHead", saTokenInfo.getTokenName());
        return tokenMap;
    }

    @Override
    public Map<String, Object> getAdminInfoByToken(SaTokenInfo saTokenInfo) {
        UmsAdminEntity umsAdminEntity =
                this.findById(Convert.toLong(StpUtil.getLoginIdByToken(saTokenInfo.getTokenValue())));
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdminEntity.getUsername());
        data.put("menus", umsRoleService.getMenuList(umsAdminEntity.getId()));
        data.put("icon", umsAdminEntity.getIcon());
        List<UmsRoleEntity> roleList = this.getRoleList(umsAdminEntity.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRoleEntity::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return data;
    }

    @Override
    public PageInfo<UmsAdminEntity> list(String keyword, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        Example example = new Example(UmsAdminEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if(StrUtil.isNotEmpty(keyword)){
            criteria.andLike("username", "%"+keyword+"%");
            criteria.orLike("nickName", "%"+keyword+"%");
        }
        return new PageInfo<>(umsAdminDao.selectByExample(example));
    }

    @Override
    public int update(Long id, UmsAdminEntity admin) {
        admin.setId(id);
        UmsAdminEntity queryAdminEntity = this.findById(id);
        if(queryAdminEntity.getPassword().equals(admin.getPassword())){
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(SaSecureUtil.md5(admin.getPassword()));
            }
        }
        this.getCacheService().delAdmin(id);
        return 0;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordDto dto) {
        if(StrUtil.isEmpty(dto.getUsername())
                ||StrUtil.isEmpty(dto.getOldPassword())
                ||StrUtil.isEmpty(dto.getNewPassword())){
            return -1;
        }
        UmsAdminEntity queryAdminEntity = this.getAdminByUsername(dto.getUsername());
        if(Objects.isNull(queryAdminEntity)){
            return -2;
        }
        if(!SaSecureUtil.md5(dto.getOldPassword()).equals(queryAdminEntity.getPassword())){
            return -3;
        }
        queryAdminEntity.setPassword(SaSecureUtil.md5(dto.getNewPassword()));
        this.update(queryAdminEntity);
        getCacheService().delAdmin(queryAdminEntity.getId());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        this.getCacheService().delAdmin(id);
        // 删除用户角色关系
        umsAdminRoleRelationDao.delByAdminId(id);
        // 删除用户
        int count = this.deleteById(id);
        this.getCacheService().delResourceList(id);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int allocateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        // 删除原来的角色
        Example example = new Example(UmsAdminRoleRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adminId", adminId);
        umsAdminRoleRelationDao.deleteByExample(example);

        // 绑定新角色
        if(!CollectionUtils.isEmpty(roleIds)){
            List<UmsAdminRoleRelationEntity> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelationEntity roleRelation = new UmsAdminRoleRelationEntity();
                roleRelation.setPrimaryId();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            umsAdminRoleRelationDao.insertBatch(list);
        }
        this.getCacheService().delResourceList(adminId);
        return count;
    }

    @Override
    public List<UmsResourceEntity> getResourceList(Long adminId) {
        return umsResourceDao.getResourceList(adminId);
    }
}
