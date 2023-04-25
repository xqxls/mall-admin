package com.xqxls.mall.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * MyBatis配置类
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
@Configuration
@MapperScan({"com.xqxls.mall.mapper"})
public class MyBatisConfig {


}
