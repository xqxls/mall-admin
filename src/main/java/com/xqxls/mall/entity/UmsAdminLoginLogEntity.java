package com.xqxls.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台用户登录日志表 实体
 * 
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UmsAdminLoginLogEntity对象", description = "后台用户登录日志表")
@Table(name = "ums_admin_login_log")
public class UmsAdminLoginLogEntity implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "admin_id")
    private Long adminId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 地址
     */
    private String address;

    /**
     * 浏览器登录类型
     */
    @Column(name = "user_agent")
    private String userAgent;

    private static final long serialVersionUID = 1L;
}