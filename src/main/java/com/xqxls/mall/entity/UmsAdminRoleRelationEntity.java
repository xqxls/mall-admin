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
 * 后台用户和角色关系表 实体
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UmsAdminRoleRelationEntity对象", description = "后台用户和角色关系表")
@Table(name = "ums_admin_role_relation")
public class UmsAdminRoleRelationEntity extends BaseEntity {


    /**
     * 用户ID
     */
    @Column(name = "admin_id")
    private Long adminId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

}