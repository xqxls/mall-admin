package com.xqxls.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqxls.mall.cache.UmsAdminCacheService;
import com.xqxls.mall.entity.*;
import com.xqxls.mall.mapper.*;
import com.xqxls.mall.service.UmsRoleMenuRelationService;
import com.xqxls.mall.service.UmsRoleResourceRelationService;
import com.xqxls.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private UmsResourceDao umsResourceDao;

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsRoleMenuRelationService umsRoleMenuRelationService;

    @Autowired
    private UmsRoleResourceRelationService umsRoleResourceRelationService;

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Override
    public List<UmsMenuEntity> getMenuList(Long adminId) {

        return umsMenuDao.getMenuList(adminId);
    }

    @Override
    public int create(UmsRoleEntity umsRoleEntity) {
        umsRoleEntity.setPrimaryId();
        umsRoleEntity.setCreateTime(new Date());
        umsRoleEntity.setAdminCount(0);
        umsRoleEntity.setSort(0);
        return this.add(umsRoleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        // 删除角色菜单关系
        umsRoleMenuRelationService.deleteByRoleIds(ids);
        // 删除角色资源关系
        umsRoleResourceRelationService.deleteByRoleIds(ids);
        // 删除角色
        this.deleteByIdList(ids);
    }

    @Override
    public PageInfo<UmsRoleEntity> list(String keyword, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        Example example = new Example(UmsRoleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if(StrUtil.isNotEmpty(keyword)){
            criteria.andLike("name", "%"+keyword+"%");
        }
        return new PageInfo<>(umsRoleDao.selectByExample(example));
    }

    @Override
    public List<UmsMenuEntity> listMenu(Long roleId) {
        return umsMenuDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<UmsResourceEntity> listResource(Long roleId) {
        return umsResourceDao.getResourceListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int allocMenu(Long roleId, List<Long> menuIds) {
        // 删除原来的菜单
        umsRoleMenuRelationService.deleteByRoleId(roleId);

        // 绑定新菜单
        if(!CollectionUtils.isEmpty(menuIds)){
            List<UmsRoleMenuRelationEntity> list = new ArrayList<>();
            for (Long menuId : menuIds) {
                UmsRoleMenuRelationEntity menuRelation = new UmsRoleMenuRelationEntity();
                menuRelation.setPrimaryId();
                menuRelation.setRoleId(roleId);
                menuRelation.setMenuId(menuId);
                list.add(menuRelation);
            }
            umsRoleMenuRelationService.insertBatch(list);
        }
        return menuIds.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int allocResource(Long roleId, List<Long> resourceIds) {
        // 删除原来的资源
        umsRoleResourceRelationService.deleteByRoleId(roleId);

        // 绑定新资源
        if(!CollectionUtils.isEmpty(resourceIds)){
            List<UmsRoleResourceRelationEntity> list = new ArrayList<>();
            for (Long resourceId : resourceIds) {
                UmsRoleResourceRelationEntity resourceRelation = new UmsRoleResourceRelationEntity();
                resourceRelation.setPrimaryId();
                resourceRelation.setRoleId(roleId);
                resourceRelation.setResourceId(resourceId);
                list.add(resourceRelation);
            }
            umsRoleResourceRelationService.insertBatch(list);
        }
        adminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }
}
