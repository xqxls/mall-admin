package com.xqxls.mall.common.config;

import cn.hutool.core.lang.Snowflake;
import com.xqxls.mall.common.id.BaseEntity;
import com.xqxls.mall.common.id.IdWorker;
import com.xqxls.mall.common.id.SnowIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.Objects;
import java.util.Random;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 19:08
 */
@Slf4j
@Configuration
public class IdWorkerAutoConfiguration {


    private static final String REDIS_KEY = "IdWorker:";

    /**
     * 数据中心环境变量名称
     */
    private static final String DATA_CENTER_EVN_KEY = "SNOW_DATA_CENTER_ID";

    /**
     * 机器id环境变量名称
     */
    private static final String SNOW_WORKER_ENV_KEY = "SNOW_WORKER_ID";

    private SnowIdWorker snowIdWorker;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Bean
    public IdWorker idWorker() {
        BaseEntity.setIdWorker(snowIdWorker());
        return snowIdWorker;
    }
    @Value("${spring.application.name}")
    private String serverName;

    @Bean
    public SnowIdWorker snowIdWorker() {
        int dataCenterId = getDataCenterId();
        int workerId = getWorkerId();
        log.info("create SnowIdWorker workerId [{}],dataCenterId [{}] ", workerId, dataCenterId);
        Snowflake snowflake = new Snowflake(workerId, dataCenterId);
        SnowIdWorker.setSnowflake(snowflake);
        snowIdWorker = new SnowIdWorker();
        SnowIdWorker.setSnowflake(snowflake);
        return snowIdWorker;
    }


    private int getWorkerId() {
        String dataCenterId = System.getenv(SNOW_WORKER_ENV_KEY);
        if (dataCenterId != null) {
            try {
                int integer = Integer.parseInt(dataCenterId);
                log.info("使用环境变量 [{}] [{}]作为机器id ", SNOW_WORKER_ENV_KEY, dataCenterId);
                return integer;
            } catch (NumberFormatException e) {
                log.error("环境变量 [{}] [{}]不是数字", SNOW_WORKER_ENV_KEY, dataCenterId);
            }
        }
        log.info("环境变量[{}]不存在", SNOW_WORKER_ENV_KEY);
        if (redisTemplate == null) {
            int i = new Random().nextInt(31);
            log.info("使用随机数[{}]作为机器id", i);
            return i;
        }
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(REDIS_KEY + serverName, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        long l = redisAtomicLong.incrementAndGet();
        int i = (int) (l % 32);
        log.info("使用自增值[{}]作为机器id, redisAtomicLong [{}]", i, l);
        return i;
    }

    private int getDataCenterId() {
        String dataCenterId = System.getenv(DATA_CENTER_EVN_KEY);
        if (dataCenterId == null) {
            log.info("环境变量[{}]不存在,使用默认值[1]作为数据中心id ", DATA_CENTER_EVN_KEY);
            return 1;
        }
        try {
            int integer = Integer.parseInt(dataCenterId);
            log.info("使用环境变量 [{}] [{}]作为数据中心id ", DATA_CENTER_EVN_KEY, dataCenterId);
            return integer;
        } catch (NumberFormatException e) {
            log.error("环境变量 [{}] [{}]不是数字,使用默认值[1]作为数据中心id ", DATA_CENTER_EVN_KEY, dataCenterId);
            return 1;
        }
    }

}
