package com.xqxls.mall.convert.mapstruct;

import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.entity.UmsAdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UmsAdminMapper {

    UmsAdminMapper INSTANCE = Mappers.getMapper(UmsAdminMapper.class);

    UmsAdminEntity umsAdminRegisterToEntity(UmsAdminRegisterDto umsAdminRegisterDto);
}
