package com.xqxls.mall.aop.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义注解，有该注解的缓存方法会抛出异常
 * @Author: huzhuo
 * @Date: Created in 2023/6/13 19:44
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
