package com.xqxls.mall.domain;

import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 胡卓
 * @create 2023-04-25 13:35
 * @Description
 */
public class AdminUserDetails implements UserDetails {

    private UmsAdminEntity umsAdminEntity;
    private List<UmsResourceEntity> resourceList;

    public AdminUserDetails(UmsAdminEntity umsAdminEntity, List<UmsResourceEntity> resourceList) {
        this.umsAdminEntity = umsAdminEntity;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的资源（资源id+name作为凭证）
        return resourceList.stream()
                .map(resource ->new SimpleGrantedAuthority(resource.getId()+":"+resource.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdminEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdminEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdminEntity.getStatus().equals(1);
    }
}

