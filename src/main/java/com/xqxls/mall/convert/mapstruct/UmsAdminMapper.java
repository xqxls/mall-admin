package com.xqxls.mall.convert.mapstruct;

import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.entity.UmsAdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: 用户实体转换类
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 18:51
 */
@Mapper
public interface UmsAdminMapper {

    UmsAdminMapper INSTANCE = Mappers.getMapper(UmsAdminMapper.class);

    /**
     * 用户注册dto转实体
     * @param umsAdminRegisterDto 用户注册dto
     * @return 用户实体
     */
    UmsAdminEntity umsAdminRegisterToEntity(UmsAdminRegisterDto umsAdminRegisterDto);
}
