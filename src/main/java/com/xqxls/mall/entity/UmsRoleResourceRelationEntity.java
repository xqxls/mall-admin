package com.xqxls.mall.entity;

import com.xqxls.mall.common.id.BaseEntity;
import com.xqxls.mall.common.id.IdWorker;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台角色资源关系表 实体
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UmsRoleResourceRelationEntity对象", description = "后台角色资源关系表")
@Table(name = "ums_role_resource_relation")
public class UmsRoleResourceRelationEntity extends BaseEntity {


    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;

}