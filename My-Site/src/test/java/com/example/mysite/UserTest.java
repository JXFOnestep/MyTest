package com.example.mysite;

import com.example.mysite.dao.UserDao;
import com.example.mysite.entity.User;
import com.example.mysite.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:21
 * @description
 */
public class UserTest extends BasicTest {
    @Autowired
    UserService userService;
    @Test
    void testUserService() {
        List<User> all = userService.findAll();
        System.out.println(all);
    }

    @Test
    void testLogin() {
        User admin = userService.login("admin", "123456");
        System.out.println(admin);
    }
}
