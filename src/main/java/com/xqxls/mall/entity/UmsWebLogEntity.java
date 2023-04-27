package com.xqxls.mall.entity;

import com.xqxls.mall.common.id.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 胡卓
 * @create 2023-04-27 15:03
 * @Description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UmsWebLogEntity对象", description = "方法监控日志表")
@Table(name = "ums_web_log")
public class UmsWebLogEntity extends BaseEntity {

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 方法
     */
    private String method;

    /**
     * 参数
     */
    private String param;

    /**
     * 耗时
     */
    @Column(name = "spend_time")
    private Long spendTime;
}
