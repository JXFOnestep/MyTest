package com.example.mysite.service.user.impl;

import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.dao.UserDao;
import com.example.mysite.entity.User;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.user.UserService;
import com.example.mysite.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:07
 * @description
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);

        //这里对 账户+密码 进行MD5加密，作为存储在数据库中密码，
        //比如 admin 账户，密码是 123456
        //实际就是对 "admin123456" 这个串进行MD5加密，加密结果存储在数据库中
        String pwd = TaleUtils.MD5encode(username + password);
        User user = userDao.getUserInfoByCond(username, pwd);
        if (null == user) //抛出自定义异常
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);

        return user;
    }

    @Override
    public User getUserInfoById(Integer uId) {
        return userDao.getUserInfoById(uId);
    }
}
