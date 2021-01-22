package com.xybh.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 18:34 2021/1/21
 * @Modified:
 */
@Aspect
@Component
public class ServiceLogAspect {

    public static  final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);
    /**
     * AOP通知:
     * 1. 前置通知: 在方法调用之前仔细
     * 2. 后置通知: 在方法正常调用之后执行
     * 3. 环绕通知: 在方法调用之前和之后,都分别可以执行的通知
     * 4. 异常通知: 如果在憨憨调用过程中发生异常,则通知
     * 5. 最终通知: 在方法调用之后执行
     */

    /**
     * 切面表达式:
     * execution 代表所要执行的表达式主体
     * 第一处 * 表示方法返回类型
     * 第二次 包名表示aop监控的包名
     * 第三处 .. 代表该包以及子包下的所有类方法
     * 第四次 *  代表所有类
     * 第五处 *(..) *代表方法 (..)代表参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.xybh.service.impl..*.*(..) )")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===== 开始执行 {}.{} =====",
                    joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName());
        long start = System.currentTimeMillis();

        // 执行目标 sevice
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long takeTime = end - start;
        if(takeTime > 3000){
            log.error("===== 执行结束, 耗时: {}毫秒", takeTime);
        }else if(takeTime > 2000){
            log.warn("===== 执行结束, 耗时: {}毫秒", takeTime);
        }else{
            log.info("===== 执行结束, 耗时: {}毫秒", takeTime);
        }
        return result;
    }
}
