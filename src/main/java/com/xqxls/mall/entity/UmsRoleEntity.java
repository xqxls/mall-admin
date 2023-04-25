package com.xqxls.mall.entity;

import com.xqxls.mall.common.id.BaseEntity;
import io.swagger.annotations.ApiModel;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台用户角色表 实体
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UmsRoleEntity对象", description = "后台用户角色表")
@Table(name = "ums_role")
public class UmsRoleEntity extends BaseEntity {


    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 后台用户数量
     */
    @Column(name = "admin_count")
    private Integer adminCount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 启用状态：0->禁用；1->启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

}