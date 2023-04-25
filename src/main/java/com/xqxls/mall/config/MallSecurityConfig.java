package com.xqxls.mall.config;

import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.security.component.DynamicSecurityService;
import com.xqxls.mall.service.UmsAdminService;
import com.xqxls.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 * 自定义配置，用于配置如何获取用户信息及动态权限
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
@Configuration
public class MallSecurityConfig {

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsResourceService resourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResourceEntity> resourceList = resourceService.findAll();
                for (UmsResourceEntity resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
