package com.xqxls.mall.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.xqxls.mall.entity.UmsWebLogEntity;
import com.xqxls.mall.mapper.UmsWebLogDao;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;


/**
 * @author 胡卓
 * @create 2022-08-30 16:35
 * @Description 自定义日志切面
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Autowired
    private UmsWebLogDao umsWebLogDao;

    @Pointcut("@annotation(com.xqxls.mall.aop.annotation.WebLog)")
    public void webLog(){}

    @Around("webLog()")
    public Object saveSysLog(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("环绕通知开始。。。。。");
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        log.info(className + "." + methodName);
        //请求的参数
        Object[] args = proceedingJoinPoint.getArgs();
        String params = JSON.toJSONString(args);
        log.info(params);
        //记录时间
        long start = System.currentTimeMillis();
        Object result =null;
        try {
            result = proceedingJoinPoint.proceed();
            System.out.println(result.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println(throwable.getMessage());
        }
        long time = System.currentTimeMillis() - start;
        log.info("方法耗时 : "+time);

        // 后续可以改造成消息队列方法落库
        UmsWebLogEntity umsWebLogEntity = new UmsWebLogEntity();
        umsWebLogEntity.setPrimaryId();
        umsWebLogEntity.setCreateTime(new Date());
        umsWebLogEntity.setMethod(className + "." + methodName);
        umsWebLogEntity.setParam(params);
        umsWebLogEntity.setSpendTime(time);
        umsWebLogDao.insert(umsWebLogEntity);
        log.info("环绕通知结束。。。。。");
        return result;
    }

}


