package com.example.mysite.controller;

import com.example.mysite.entity.User;
import com.example.mysite.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 21:07
 * @description
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/user/all")
    public String toUser(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList",userList);
        System.out.println("get userList success ! ");
        return "user/user_main";
    }

}
