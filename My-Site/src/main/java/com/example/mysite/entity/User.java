package com.example.mysite.entity;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 21:29
 * @description
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7103385264090176370L;
    private int uid; //用户编号
    private String username; //账号
    private String password; //密码
    private String email; //邮箱
    private String homeUrl; //主页地址
    private String screenName; //昵称
    private int created; //用户注册时的GMT unix时间戳
    private int activated; //最后活动时间
    private int logged; //上次登录的时间
    private String groupName; //用户组

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getActivated() {
        return activated;
    }

    public void setActivated(int activated) {
        this.activated = activated;
    }

    public int getLogged() {
        return logged;
    }

    public void setLogged(int logged) {
        this.logged = logged;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", homeUrl='" + homeUrl + '\'' +
                ", screenName='" + screenName + '\'' +
                ", created=" + created +
                ", activated=" + activated +
                ", logged=" + logged +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
