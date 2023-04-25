package com.xqxls.mall.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置白名单资源路径
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
@Getter
@Setter
@EnableConfigurationProperties(IgnoreUrlsConfig.class)
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
