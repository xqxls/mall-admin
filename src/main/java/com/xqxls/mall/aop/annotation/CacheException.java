package com.xqxls.mall.aop.annotation;

import java.lang.annotation.*;

/**
 * @author 胡卓
 * @create 2022-08-30 16:35
 * @Description 自定义注解，有该注解的缓存方法会抛出异常
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
