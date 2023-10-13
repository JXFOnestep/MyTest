package com.example.mysite.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 19:58
 * @description 将拦截器添加到容器中
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private BaseInterceptor baseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
//       addPathPatterns()配置我们要拦截哪些路径 addPathPatterns("/**")表示拦截所有请求，包括我们的静态资源
//                .addPathPatterns()
//       excludePathPatterns()表示我们要放行哪些（表示不用经过拦截器）
//       excludePathPatterns("/","/login")表示放行“/”与“/login”请求
//       如果有静态资源的时候可以在这个地方放行
//                .excludePathPatterns("/","/login");

    }
}
