package com.xqxls.mall.controller;

import com.github.pagehelper.PageInfo;
import com.xqxls.mall.common.api.CommonPage;
import com.xqxls.mall.common.api.CommonResult;
import com.xqxls.mall.dto.node.UmsMenuNode;
import com.xqxls.mall.entity.UmsMenuEntity;
import com.xqxls.mall.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台菜单表 前端控制器
 *
 * @author xqxls
 * @date 2023-04-25 9:20 上午
 */
@Api(tags = "后台菜单表前端控制器")
@RestController
@RequestMapping("/ums-menu")
public class UmsMenuController {

    @Autowired
    private UmsMenuService umsMenuService;

    @ApiOperation("添加后台菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Void> create(@RequestBody UmsMenuEntity umsMenuEntity) {
        int count = umsMenuService.create(umsMenuEntity);
        if (count>0) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改后台菜单")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult<Void> update(@PathVariable Long id, @RequestBody UmsMenuEntity umsMenuEntity) {
        int count = umsMenuService.update(id, umsMenuEntity);
        if (count>0) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID获取菜单详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsMenuEntity> getItem(@PathVariable Long id) {
        UmsMenuEntity umsMenu = umsMenuService.findById(id);
        return CommonResult.success(umsMenu);
    }

    @ApiOperation("根据ID删除后台菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult<Void> delete(@PathVariable Long id) {
        int count = umsMenuService.deleteById(id);
        if (count>0) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页查询后台菜单")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsMenuEntity>> list(@PathVariable Long parentId,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageInfo<UmsMenuEntity> menuList = umsMenuService.list(parentId, page, size);
        return CommonResult.success(CommonPage.restPage(menuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsMenuNode>> treeList() {
        return CommonResult.success(umsMenuService.treeList());
    }

    @ApiOperation("修改菜单显示状态")
    @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult<Integer> updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        int count = umsMenuService.updateHidden(id, hidden);
        if (count>0) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

}
