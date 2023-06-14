package com.xqxls.mall.common.id;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 18:51
 */
public interface IdWorker {

    /**
     * 生成雪花ID
     * @return 雪花ID
     */
    long nextId();
}
