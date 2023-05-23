package com.example.mysite.controller;

import com.example.mysite.entity.Option;
import com.example.mysite.service.option.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:09
 * @description
 */
@Controller
@RequestMapping("/option")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @GetMapping("/all")
    public String findAll(Model model) {
        List<Option> all = optionService.getAllOptions();
        model.addAttribute("optionList",all);
        return "option/option_main";
    }

}
