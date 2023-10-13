package com.example.mysite.handler;

import com.example.mysite.exception.BusinessException;
import com.example.mysite.utils.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Xufeng Jiang
 * @date 2023年05月06日 9:45
 * @description 全局异常处理
 * @ControllerAdvice 是一个用于全局异常处理的注解。通过在类上使用该注解，可以将其中定义的方法应用到所有使用了
 * @RequestMapping 注解的控制器中，并对发生的异常进行统一处理。
 * @ControllerAdvice的作用可以分为以下几个方面
 * 1. 异常处理
 * 使用 @ExceptionHandler 注解定义的方法可以对指定的异常类型进行统一处理，从而避免了在每个控制器方法中都需要捕获和处理同样的异常的麻烦。
 *
 * 2. 统一数据绑定
 * 使用 @InitBinder 注解定义的方法可以将请求参数进行统一预处理，例如格式化、类型转换等操作。
 *
 * 3. 全局数据处理
 * 使用 @ModelAttribute 注解定义的方法可以在所有请求处理之前进行处理，可以在这里获取一些公共的数据并将其注入到所有请求中
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);


    //使用 @ExceptionHandler 注解定义的方法可以对指定的异常类型进行统一处理，从而避免了在每个控制器方法中都需要捕获和处理同样的异常的麻烦。
    @ExceptionHandler(BusinessException.class) // 捕获所有异常
    @ResponseBody
    public APIResponse handleException(Exception e) {
        String msg = "请求错误";
        if (e instanceof BusinessException){
            msg = ((BusinessException) e).getErrorCode();
        }
        logger.error("find exception:e={}",msg);
//        e.printStackTrace();
        return APIResponse.fail(msg);
    }

//    // 全局数据处理
//    @ModelAttribute
//    public void addAttributes(Model model) {
//        model.addAttribute("appName", "My Application");
//    }
//
//    // 全局数据绑定
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }
}
