package com.xqxls.mall.service.impl;

import com.xqxls.mall.entity.UmsRoleResourceRelationEntity;
import com.xqxls.mall.mapper.UmsRoleResourceRelationDao;
import com.xqxls.mall.service.UmsRoleResourceRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 后台角色资源关系表 服务实现类
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Service
public class UmsRoleResourceRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationDao, UmsRoleResourceRelationEntity> implements UmsRoleResourceRelationService {

    @Autowired
    private UmsRoleResourceRelationDao umsRoleResourceRelationDao;

    @Override
    public void deleteByRoleId(Long roleId) {
        Example example = new Example(UmsRoleResourceRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        umsRoleResourceRelationDao.deleteByExample(example);
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        Example example = new Example(UmsRoleResourceRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("roleId", roleIds);
        umsRoleResourceRelationDao.deleteByExample(example);
    }
}
