package com.example.mysite.aspect;

import com.example.mysite.service.log.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * @author Xufeng Jiang
 * @date 2023年05月05日 20:27
 * @description
 */
@Aspect
@Component
public class WebLogAspect {

    @Autowired
    private LogService logService;
    private static Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    //startTime变量用于记录当前线程的请求开始时间，可以在后续的代码中直接使用，而不用在各个方法之间频繁地传递参数。
    //ThreadLocal是Java中的一个线程本地存储类，它可以为每一个线程创建一个独立的变量副本，各个线程之间互不干扰，从而避免了线程安全问题。
    ThreadLocal<Long> startTime = new ThreadLocal<>();


    //定义一个切入点
    @Pointcut("execution(public * com.example.mysite.controller..*.*(..))")//..表示匹配类及其子包下的所有方法 .匹配所有方法名 (..) 匹配任意参数的方法
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        // 记录下请求内容
        LOGGER.info("URL : " + request.getRequestURL().toString());
        LOGGER.info("HTTP_METHOD : " + request.getMethod());
        LOGGER.info("IP : " + request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE : " + ret);
        LOGGER.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        startTime.remove();//用完之后记得清除，不然可能导致内存泄露;
    }
}
