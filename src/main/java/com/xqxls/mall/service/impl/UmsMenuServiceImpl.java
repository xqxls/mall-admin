package com.xqxls.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqxls.mall.convert.mapstruct.UmsMenuMapper;
import com.xqxls.mall.dto.node.UmsMenuNode;
import com.xqxls.mall.entity.UmsMenuEntity;
import com.xqxls.mall.mapper.UmsMenuDao;
import com.xqxls.mall.service.UmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台菜单表 服务实现类
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Service
public class UmsMenuServiceImpl  extends ServiceImpl<UmsMenuDao, UmsMenuEntity> implements UmsMenuService {

    @Autowired
    private UmsMenuDao umsMenuDao;

    @Override
    public int create(UmsMenuEntity umsMenuEntity) {
        umsMenuEntity.setPrimaryId();
        umsMenuEntity.setCreateTime(new Date());
        updateLevel(umsMenuEntity);
        return this.add(umsMenuEntity);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(UmsMenuEntity umsMenuEntity) {
        if (umsMenuEntity.getParentId() == 0) {
            //没有父菜单时为一级菜单
            umsMenuEntity.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenuEntity parentMenu = this.findById(umsMenuEntity.getParentId());
            if (parentMenu != null) {
                umsMenuEntity.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenuEntity.setLevel(0);
            }
        }
    }

    @Override
    public int update(Long id, UmsMenuEntity umsMenuEntity) {
        umsMenuEntity.setId(id);
        updateLevel(umsMenuEntity);
        return this.update(umsMenuEntity);
    }

    @Override
    public PageInfo<UmsMenuEntity> list(Long parentId, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        Example example = new Example(UmsMenuEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", parentId);
        example.orderBy("sort").desc();
        return new PageInfo<>(umsMenuDao.selectByExample(example));
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenuEntity> menuList = this.findAll();
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenuEntity umsMenuEntity = new UmsMenuEntity();
        umsMenuEntity.setId(id);
        umsMenuEntity.setHidden(hidden);
        return this.update(umsMenuEntity);
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode(UmsMenuEntity menu, List<UmsMenuEntity> menuList) {
        UmsMenuNode node = UmsMenuMapper.INSTANCE.umsMenuEntityToNode(menu);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;

    }
}
