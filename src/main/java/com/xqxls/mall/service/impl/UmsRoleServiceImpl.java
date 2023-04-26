package com.xqxls.mall.service.impl;

import com.xqxls.mall.entity.UmsMenuEntity;
import com.xqxls.mall.entity.UmsRoleEntity;
import com.xqxls.mall.mapper.UmsMenuDao;
import com.xqxls.mall.mapper.UmsRoleDao;
import com.xqxls.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台用户角色表 服务实现类
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleDao, UmsRoleEntity> implements UmsRoleService {

    @Autowired
    private UmsMenuDao umsMenuDao;

    @Override
    public List<UmsMenuEntity> getMenuList(Long adminId) {

        return umsMenuDao.getMenuList(adminId);
    }
}
