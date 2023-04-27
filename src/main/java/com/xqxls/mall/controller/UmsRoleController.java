package com.xqxls.mall.controller;

import com.github.pagehelper.PageInfo;
import com.xqxls.mall.common.api.CommonPage;
import com.xqxls.mall.common.api.CommonResult;
import com.xqxls.mall.entity.UmsMenuEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.entity.UmsRoleEntity;
import com.xqxls.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户角色表 前端控制器
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Api(tags = "后台用户角色表前端控制器")
@RestController
@RequestMapping("/ums-role")
public class UmsRoleController {

    @Autowired
    private UmsRoleService umsRoleService;

    @ApiOperation("添加角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult<Void> create(@RequestBody UmsRoleEntity umsRoleEntity) {
        int count = umsRoleService.create(umsRoleEntity);
        if (count>0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public CommonResult<Void> update(@PathVariable Long id, @RequestBody UmsRoleEntity umsRoleEntity) {
        umsRoleEntity.setId(id);
        int count = umsRoleService.update(umsRoleEntity);
        if (count>0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResult<Void> delete(@RequestParam("ids") List<Long> ids) {
        umsRoleService.deleteByIds(ids);
        return CommonResult.success(null);
    }


    @ApiOperation("获取所有角色")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UmsRoleEntity>> listAll() {
        List<UmsRoleEntity> roleList = umsRoleService.findAll();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UmsRoleEntity>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageInfo<UmsRoleEntity> roleList = umsRoleService.list(keyword, page, size);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("修改角色状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.PUT)
    public CommonResult<Void> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsRoleEntity umsRoleEntity = new UmsRoleEntity();
        umsRoleEntity.setId(id);
        umsRoleEntity.setStatus(status);
        int count = umsRoleService.update(umsRoleEntity);
        if (count>0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    public CommonResult<List<UmsMenuEntity>> listMenu(@PathVariable Long roleId) {
        List<UmsMenuEntity> menuList = umsRoleService.listMenu(roleId);
        return CommonResult.success(menuList);
    }

    @ApiOperation("获取角色相关资源")
    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    public CommonResult<List<UmsResourceEntity>> listResource(@PathVariable Long roleId) {
        List<UmsResourceEntity> resourceList = umsRoleService.listResource(roleId);
        return CommonResult.success(resourceList);
    }

    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    public CommonResult<Integer> allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = umsRoleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    public CommonResult<Integer> allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = umsRoleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }

}
