package com.example.mysite.controller.admin;

import com.example.mysite.annotation.MyLog;
import com.example.mysite.dto.StatisticsDto;
import com.example.mysite.entity.Comment;
import com.example.mysite.entity.Content;
import com.example.mysite.entity.Log;
import com.example.mysite.service.admin.SiteService;
import com.example.mysite.service.log.LogService;
import com.example.mysite.utils.TimeUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 15:36
 * @description 后台首页
 */
@Api("后台首页")
@Controller
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private LogService logService;

    @Autowired
    private SiteService siteService;

    @ApiOperation("进入首页")
    @MyLog
    @GetMapping(value = {"","/index"})
    public String index(HttpServletRequest request){
        System.out.println("Enter admin index method " + TimeUtil.getTime());
//        LOGGER.info("Enter admin index method");
        List<Comment> comments = siteService.getComments(5);
        List<Content> contents = siteService.getNewArticles(5);
        StatisticsDto statistics = siteService.getStatistics();
        // 取最新的20条日志
        PageInfo<Log> logs = logService.getLogs(1, 5);
        List<Log> list = logs.getList();
        request.setAttribute("comments", comments);
        request.setAttribute("articles", contents);
        request.setAttribute("statistics", statistics);
        request.setAttribute("logs", list);
//        LOGGER.info("Exit admin index method");
        return "admin/index";
    }

}
