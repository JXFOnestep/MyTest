package com.example.mysite.controller;

import com.example.mysite.entity.Relationships;
import com.example.mysite.service.relation.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:21
 * @description
 */
@Controller
@RequestMapping("/relation")
public class RelationshipsController {

    @Autowired
    private RelationService relationService;

    @GetMapping("/all")
    public String all(Model model) {
        List<Relationships> all = relationService.findAll();
        model.addAttribute("relationShipList",all);
        return "relation/relation_main";
    }
}
