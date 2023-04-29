package com.xqxls.mall.component;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.convert.Convert;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.entity.UmsRoleEntity;
import com.xqxls.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 自定义权限验证接口扩展
 * @Author: huzhuo
 * @Date: Created in 2023/4/29 16:17
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UmsAdminService umsAdminService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<UmsResourceEntity> umsResourceList= umsAdminService.getResourceList(Convert.toLong(loginId));
        return umsResourceList.stream().map(UmsResourceEntity::getUrl).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<UmsRoleEntity> umsRoleList= umsAdminService.getRoleList(Convert.toLong(loginId));
        return umsRoleList.stream().map(UmsRoleEntity::getName).collect(Collectors.toList());
    }
}
