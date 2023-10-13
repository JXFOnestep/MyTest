package com.example.mysite.controller;

import com.example.mysite.constant.LogActions;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.entity.User;
import com.example.mysite.service.log.LogService;
import com.example.mysite.service.user.UserService;
import com.example.mysite.utils.APIResponse;
import com.example.mysite.utils.TaleUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 9:03
 * @description
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @ApiOperation("登陆操作")
    @PostMapping("/login")
    @ResponseBody
    public APIResponse toLogin(HttpServletRequest request, HttpServletResponse response,
                               @ApiParam(name = "username", value = "用户名", required = true)
                               @RequestParam(name = "username", required = true)
                                       String username,
                               @ApiParam(name = "password", value = "密码", required = true)
                               @RequestParam(name = "password", required = true)
                                       String password,
                               @ApiParam(name = "rememberMe", required = false)
                               @RequestParam(name = "rememberMe", required = false)
                                       String remeber_me) {
        //配置了全局捕获异常，所以这里可以不用try catch
//        try {
            User userInfo = userService.login(username, password);
            request.getSession().setAttribute(WebConstant.LOGIN_SESSION_KEY, userInfo);
            // 添加日志
            //如果点击了记住我按钮，设置创建cookie
            if (StringUtils.isNotBlank(remeber_me)) {
                TaleUtils.setCookie(response, userInfo.getUid());
            }
            logService.addLog(LogActions.LOGIN.getAction(), null, userInfo.getUid(), request.getRemoteAddr());
//        } catch (Exception e) {
//            String msg = "登录失败";
//            if (e instanceof BusinessException) {
//                msg = e.getMessage();
//                msg = "用户名获取密码错误";
//            }
//            LOGGER.error(msg, e);
//            return APIResponse.fail(msg);
//        }

        return APIResponse.success();
    }

}
