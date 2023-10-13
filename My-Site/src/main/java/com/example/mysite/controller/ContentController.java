package com.example.mysite.controller;

import com.example.mysite.entity.Content;
import com.example.mysite.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:35
 * @description
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/all")
    public String allContents(Model model) {
        List<Content> all = contentService.findAll();
        model.addAttribute("contentList",all);
        return "content/content_main";
    }
}
