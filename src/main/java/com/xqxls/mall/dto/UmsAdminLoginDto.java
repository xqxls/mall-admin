package com.xqxls.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author 胡卓
 * @create 2023-04-25 13:27
 * @Description
 */
@Data
public class UmsAdminLoginDto {

    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "用户头像")
    private String icon;

    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "备注")
    private String note;
}
