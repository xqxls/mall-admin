package com.xqxls.mall.common.api;

import cn.hutool.core.convert.Convert;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页数据封装类
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将MyBatis Plus 分页结果转化为通用结果
     */
    public static <T> CommonPage<T> restPage(Page<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(Convert.toInt(pageResult.getNumber()));
        result.setPageSize(Convert.toInt(pageResult.getSize()));
        result.setTotal(pageResult.getTotalElements());
        result.setTotalPage(Convert.toInt(pageResult.getTotalElements()/pageResult.getSize()+1));
        result.setList(pageResult.getContent());
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
