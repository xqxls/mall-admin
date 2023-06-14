package com.xqxls.mall.convert.mapstruct;

import com.xqxls.mall.dto.node.UmsMenuNode;
import com.xqxls.mall.entity.UmsMenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: 菜单转换类
 * @Author: huzhuo
 * @Date: Created in 2023/4/26 20:36
 */
@Mapper
public interface UmsMenuMapper {

    UmsMenuMapper INSTANCE = Mappers.getMapper(UmsMenuMapper.class);

    /**
     * 菜单实体转菜单节点
     * @param umsMenuEntity 菜单实体
     * @return 菜单节点类
     */
    UmsMenuNode umsMenuEntityToNode(UmsMenuEntity umsMenuEntity);
}
