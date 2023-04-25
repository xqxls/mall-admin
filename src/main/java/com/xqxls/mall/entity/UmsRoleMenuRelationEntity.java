package com.xqxls.mall.entity;

import com.xqxls.mall.common.id.BaseEntity;
import io.swagger.annotations.ApiModel;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台角色菜单关系表 实体
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UmsRoleMenuRelationEntity对象", description = "后台角色菜单关系表")
@Table(name = "ums_role_menu_relation")
public class UmsRoleMenuRelationEntity extends BaseEntity {

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;

}