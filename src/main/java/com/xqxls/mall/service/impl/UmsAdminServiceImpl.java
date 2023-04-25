package com.xqxls.mall.service.impl;

import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.common.exception.Asserts;
import com.xqxls.mall.domain.AdminUserDetails;
import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsAdminLoginLogEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.mapper.UmsAdminDao;
import com.xqxls.mall.mapper.UmsAdminLoginLogDao;
import com.xqxls.mall.mapper.UmsResourceDao;
import com.xqxls.mall.service.UmsAdminService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
    private UmsResourceDao umsResourceDao;

    @Autowired
    private UmsAdminLoginLogDao umsAdminLoginLogDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
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
        if(umsAdminEntity==null) return;
        UmsAdminLoginLogEntity umsAdminLoginLogEntity = new UmsAdminLoginLogEntity();
        umsAdminLoginLogEntity.setPrimaryId();
        umsAdminLoginLogEntity.setAdminId(umsAdminEntity.getId());
        umsAdminLoginLogEntity.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes!=null){
            HttpServletRequest request = attributes.getRequest();
            umsAdminLoginLogEntity.setIp(request.getRemoteAddr());
        }
        umsAdminLoginLogDao.insert(umsAdminLoginLogEntity);
    }

    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UmsAdminEntity umsAdminEntity = getAdminByUsername(username);
        if (umsAdminEntity != null) {
            List<UmsResourceEntity> resourceList = getResourceList(umsAdminEntity.getId());
            return new AdminUserDetails(umsAdminEntity,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public UmsAdminEntity getAdminByUsername(String username) {
        UmsAdminEntity admin = getCacheService().getAdmin(username);
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
    public UmsAdminCacheService getCacheService() {
        return SpringUtil.getBean(UmsAdminCacheService.class);
    }

    @Override
    public List<UmsResourceEntity> getResourceList(Long adminId) {
        return umsResourceDao.getResourceList(adminId);
    }
}
