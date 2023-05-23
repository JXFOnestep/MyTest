package com.example.mysite.dto.condition;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月26日 11:10
 * @description 用户查找条件
 */
public class UserCond implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
