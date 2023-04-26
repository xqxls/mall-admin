package com.xqxls.mall.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/26 19:44
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface InsertListExtMapper<T> {
    /**
     * 批量插入全部字段，包括主键
     *
     * @param recordList 数据列表
     * @return
     */
    @Options(keyProperty = "id")
    @InsertProvider(type = SpecialSqlExtProvider.class, method = "insertBatch")
    int insertBatch(List<? extends T> recordList);

}
