package com.xqxls.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @author 胡卓
 * @create 2023-04-25 13:27
 * @Description
 */
@Data
public class UmsAdminLoginDto {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
