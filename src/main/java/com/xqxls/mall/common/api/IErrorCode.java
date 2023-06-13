package com.xqxls.mall.common.api;

/**
 * 封装API的错误码
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public interface IErrorCode {

    /**
     * 获取code
     * @return
     */
    long getCode();

    /**
     * 获取消息
     * @return
     */
    String getMessage();
}
