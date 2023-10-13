package com.example.mysite.entity;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 21:51
 * @description 日志类
 */
public class Log implements Serializable {

    private static final long serialVersionUID = -6058296500733769773L;
    private int id; //日志主键
    private String action; //产生的动作
    private String data; //产生的数据
    private int authorId; //发生人的id
    private String ip; //ip地址
    private int created; //日志创建的时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", data='" + data + '\'' +
                ", authorId=" + authorId +
                ", ip='" + ip + '\'' +
                ", created=" + created +
                '}';
    }
}
