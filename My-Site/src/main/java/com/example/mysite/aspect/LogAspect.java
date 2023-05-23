package com.example.mysite.aspect;

import com.alibaba.fastjson2.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月26日 21:08
 * @description 日志切入，使用 @MyLog注解，则运行该方法时在控制台打印相关信息
 */
@Component
@Aspect
public class LogAspect {

    //定义一个切入点，这里即使用了@MyLog的方法会被切入
    @Pointcut("@annotation(com.example.mysite.annotation.MyLog)")
    public void myLogAspect() {}

    @Before("myLogAspect()") //在进入这个切入点之前，执行下面的方法
    public void beforeMyLog(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String methodName = joinPoint.getSignature().getName();
        System.out.println("========================================= Method " + methodName
                + "() begin =========================================");

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS");
        String time = formatter.format(localDateTime);
        System.out.println("Time:        " + time);
        //打印请求URL
        System.out.println("URL:         " + request.getRequestURL());
        //打印请求方法
        System.out.println("Http Method:   " + request.getMethod());
        //打印controller的全路径以及执行方法
        System.out.println("Class Method:   " + joinPoint.getSignature().getDeclaringTypeName() + "." + methodName);
        //打印请求的IP
        System.out.println("IP:  " + request.getRemoteHost());
        Object[] args = joinPoint.getArgs();

        Object[] args_after_filter = new Object[args.length];
        //ServletRequest，ServletResponse，MultipartFile不能被序列化，需要排除之后再做序列化。
        int i = 0;
        for(Object arg : args) {
            if(arg instanceof ServletRequest ||
                arg instanceof ServletResponse ||
                arg instanceof MultipartFile) {
                continue;
            }
            args_after_filter[i++] = arg;
        }
        List<Integer> list = new LinkedList<>();


        //打印请求入参
        System.out.println("Request Args:  "  + JSON.toJSONString(args_after_filter) );
        System.out.println("=============================================================================");
    }

    @After("myLogAspect()")
    public void afterMyLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("====================================After MyLog===============================");
    }
}
