package com.xqxls.mall.common.exception;

import com.xqxls.mall.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
