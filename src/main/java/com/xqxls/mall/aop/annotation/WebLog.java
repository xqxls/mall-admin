package com.xqxls.mall.aop.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义日志，记录接口操作信息
 * @Author: huzhuo
 * @Date: Created in 2023/6/13 19:44
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WebLog {

}
