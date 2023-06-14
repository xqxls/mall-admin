package com.xqxls.mall.common.id;

import cn.hutool.core.lang.Snowflake;
import tk.mybatis.mapper.genid.GenId;

/**
 * @Description: 雪花算法
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 18:50
 */
public class SnowIdWorker implements IdWorker, GenId<Long> {

    private static Snowflake snowflake;

    public static void setSnowflake(Snowflake snowflake) {
        SnowIdWorker.snowflake = snowflake;
    }

    @Override
    public long nextId() {
        return snowflake.nextId();
    }

    @Override
    public Long genId(String s, String s1) {
        return snowflake.nextId();
    }
}
