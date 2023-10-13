package com.example.mysite.controller;

import com.example.mysite.entity.Attachment;
import com.example.mysite.service.attachment.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:29
 * @description
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    private AttachService attachService;

    @GetMapping("/all")
    public String all(Model model) {
        List<Attachment> all = attachService.findAll();
        model.addAttribute("attachList",all);
        return "attach/attach_main";
    }
}
