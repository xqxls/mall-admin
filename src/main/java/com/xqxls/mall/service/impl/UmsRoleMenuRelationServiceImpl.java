package com.xqxls.mall.service.impl;

import com.xqxls.mall.entity.UmsRoleMenuRelationEntity;
import com.xqxls.mall.mapper.UmsRoleMenuRelationDao;
import com.xqxls.mall.service.UmsRoleMenuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 后台角色菜单关系表 服务实现类
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationDao, UmsRoleMenuRelationEntity> implements UmsRoleMenuRelationService {

    @Autowired
    private UmsRoleMenuRelationDao umsRoleMenuRelationDao;

    @Override
    public void deleteByRoleId(Long roleId) {
        Example example = new Example(UmsRoleMenuRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        umsRoleMenuRelationDao.deleteByExample(example);
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        Example example = new Example(UmsRoleMenuRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("roleId", roleIds);
        umsRoleMenuRelationDao.deleteByExample(example);
    }
}
