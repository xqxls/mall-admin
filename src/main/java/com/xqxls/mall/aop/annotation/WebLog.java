package com.xqxls.mall.aop.annotation;

import java.lang.annotation.*;

/**
 * @author 胡卓
 * @create 2022-08-30 16:35
 * @Description 自定义日志注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WebLog {

}
