package com.example.mysite.interceptor;

import com.example.mysite.constant.WebConstant;
import com.example.mysite.entity.Option;
import com.example.mysite.entity.User;
import com.example.mysite.service.option.OptionService;
import com.example.mysite.service.user.UserService;
import com.example.mysite.utils.AdminCommons;
import com.example.mysite.utils.Commons;
import com.example.mysite.utils.TaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 19:55
 * @description 自定义拦截器
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {

    private static final String USER_AGENT = "user-agent";

    @Autowired
    private OptionService optionService;

    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

//        LOGGE.info("UserAgent: {}", request.getHeader(USER_AGENT));
//        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIpAddrByRequest(request));


        //请求拦截处理
        User user = TaleUtils.getLoginUser(request);
        if (null == user) { //如果user为空，先去cookie中找
            Integer uid = TaleUtils.getCookieUid(request);
            if (null != uid) {//如果cookie有，保存到session中
                //这里还是有安全隐患,cookie是可以伪造的
                user = userService.getUserInfoById(uid);
                System.out.println("get user from cookie !");
                request.getSession().setAttribute(WebConstant.LOGIN_SESSION_KEY, user);
            }
        }
        if (uri.startsWith("/admin") && !uri.startsWith("/admin/login") && null == user
                && !uri.startsWith("/admin/css") && !uri.startsWith("/admin/images")
                && !uri.startsWith("/admin/js") && !uri.startsWith("/admin/plugins")
                && !uri.startsWith("/admin/editormd")) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        //设置get请求的token
//        if (request.getMethod().equals("GET")) {
//            String csrf_token = UUID.UU64();
//            // 默认存储30分钟
//            cache.hset(Types.CSRF_TOKEN.getType(), csrf_token, uri, 30 * 60);
//            request.setAttribute("_csrf_token", csrf_token);
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Option ov = optionService.getOptionByName("site_record");
        request.setAttribute("commons", commons);//一些工具类和公共方法
        request.setAttribute("option", ov);
        request.setAttribute("adminCommons", adminCommons);
        initSiteConfig(request);

    }

    private void initSiteConfig(HttpServletRequest request) {
        if (WebConstant.initConfig.isEmpty()){
            List<Option> options = optionService.getAllOptions();
            Map<String, String> querys = new HashMap<>();
            options.forEach(option -> {
                querys.put(option.getName(), option.getValue());
            });
            WebConstant.initConfig = querys;
        }
    }


}
