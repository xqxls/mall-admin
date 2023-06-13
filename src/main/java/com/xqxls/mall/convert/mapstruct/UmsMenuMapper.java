package com.xqxls.mall.convert.mapstruct;

import com.xqxls.mall.dto.node.UmsMenuNode;
import com.xqxls.mall.entity.UmsMenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: 菜单实体转换
 * @Author: huzhuo
 * @Date: Created in 2023/6/13 20:36
 */
@Mapper
public interface UmsMenuMapper {

    UmsMenuMapper INSTANCE = Mappers.getMapper(UmsMenuMapper.class);

    /**
     * 菜单实体转节点类
     * @param umsMenuEntity
     * @return
     */
    UmsMenuNode umsMenuEntityToNode(UmsMenuEntity umsMenuEntity);
}
