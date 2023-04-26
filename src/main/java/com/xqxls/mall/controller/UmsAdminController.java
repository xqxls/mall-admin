package com.xqxls.mall.controller;

import com.xqxls.mall.common.api.CommonResult;
import com.xqxls.mall.dto.UmsAdminLoginDto;
import com.xqxls.mall.dto.UmsAdminRegisterDto;
import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户表 前端控制器
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Api(tags = "后台用户表前端控制器")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService adminService;

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Map<String,String>> login(@Validated @RequestBody UmsAdminLoginDto umsAdminLoginDto) {
        String token = adminService.login(umsAdminLoginDto.getUsername(), umsAdminLoginDto.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdminEntity> register(@Validated @RequestBody UmsAdminRegisterDto umsAdminRegisterDto) {
        UmsAdminEntity umsAdminEntity = adminService.register(umsAdminRegisterDto);
        if (umsAdminEntity == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdminEntity);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String,String>> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String,Object>> getAdminInfo(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        return CommonResult.success(adminService.getAdminInfoByPrincipal(principal));
    }

}
