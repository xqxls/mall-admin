package com.xqxls.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 胡卓
 * @create 2023-04-26 14:31
 * @Description
 */
@Data
public class UpdateAdminPasswordDto {

    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotBlank
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
