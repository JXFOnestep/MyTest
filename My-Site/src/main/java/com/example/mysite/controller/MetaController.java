package com.example.mysite.controller;

import com.example.mysite.entity.Meta;
import com.example.mysite.service.meta.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:33
 * @description  分类/标签相关
 */
@Controller
@RequestMapping("/meta")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping("/all")
    public String all(Model model) {
        List<Meta> all = metaService.findAll();
        model.addAttribute("metaList",all);
        return "meta/meta_main";
    }
}
