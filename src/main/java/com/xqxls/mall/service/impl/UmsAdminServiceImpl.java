package com.xqxls.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.common.exception.Asserts;
import com.xqxls.mall.convert.mapstruct.UmsAdminMapper;
import com.xqxls.mall.domain.AdminUserDetails;
import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.dto.UpdateAdminPasswordDto;
import com.xqxls.mall.entity.*;
import com.xqxls.mall.mapper.*;
import com.xqxls.mall.service.UmsAdminService;
import com.xqxls.mall.service.UmsRoleService;
import com.xqxls.mall.util.JwtTokenUtil;
import com.xqxls.mall.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsRoleService umsRoleService;

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = this.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    private void insertLoginLog(String username) {
        UmsAdminEntity umsAdminEntity = getAdminByUsername(username);
        if(umsAdminEntity==null) {
            return;
        }
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
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UmsAdminEntity umsAdminEntity = this.getAdminByUsername(username);
        if (umsAdminEntity != null) {
            List<UmsResourceEntity> resourceList = this.getResourceList(umsAdminEntity.getId());
            return new AdminUserDetails(umsAdminEntity,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public UmsAdminEntity getAdminByUsername(String username) {
        UmsAdminEntity admin = this.getCacheService().getAdmin(username);
        if(admin!=null) {
            return  admin;
        }
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
        String encodePassword = passwordEncoder.encode(umsAdminEntity.getPassword());
        umsAdminEntity.setPassword(encodePassword);
        this.add(umsAdminEntity);
        return umsAdminEntity;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public Map<String, Object> getAdminInfoByPrincipal(Principal principal) {
        String username = principal.getName();
        UmsAdminEntity umsAdminEntity = this.getAdminByUsername(username);
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
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        this.getCacheService().delAdmin(id);
        return this.update(admin);
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
        if(!passwordEncoder.matches(dto.getOldPassword(),queryAdminEntity.getPassword())){
            return -3;
        }
        queryAdminEntity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
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
