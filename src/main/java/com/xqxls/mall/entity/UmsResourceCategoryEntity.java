package com.xqxls.mall.entity;

import com.xqxls.mall.common.id.BaseEntity;
import io.swagger.annotations.ApiModel;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源分类表 实体
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UmsResourceCategoryEntity对象", description = "资源分类表")
@Table(name = "ums_resource_category")
public class UmsResourceCategoryEntity extends BaseEntity {


    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

}