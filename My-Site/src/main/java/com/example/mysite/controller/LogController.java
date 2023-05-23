package com.example.mysite.controller;

import com.example.mysite.entity.Log;
import com.example.mysite.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:23
 * @description
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/all")
    public String findAll(Model model) {
        List<Log> all = logService.findAll();
        model.addAttribute("logList",all);
        return "log/log_main";
    }
}
