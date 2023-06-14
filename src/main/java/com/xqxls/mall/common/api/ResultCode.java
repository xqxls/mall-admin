package com.xqxls.mall.common.api;

/**
 * 枚举了一些常用API操作码
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
public enum ResultCode implements IErrorCode {

    /** 操作成功 **/
    SUCCESS(200, "操作成功"),
    /** 操作失败 **/
    FAILED(500, "操作失败"),
    /** 参数检验失败 **/
    VALIDATE_FAILED(404, "参数检验失败"),
    /** 暂未登录或token已经过期 **/
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /** 没有相关权限 **/
    FORBIDDEN(403, "没有相关权限");

    /** 编码 **/
    private long code;
    /** 提示信息 **/
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
