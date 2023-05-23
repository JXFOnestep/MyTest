package com.example.mysite.service.user;

import com.example.mysite.entity.User;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:07
 * @description
 */
public interface UserService {
    List<User> findAll();

    User login(String username, String password);

    User getUserInfoById(Integer uId);

}
