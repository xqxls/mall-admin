package com.xqxls.mall.common.api;

/**
 * 封装API的错误码
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public interface IErrorCode {

    /**
     * 获取编码
     * @return 编码
     */
    long getCode();

    /**
     * 获取提示信息
     * @return 提示信息
     */
    String getMessage();
}
