package com.example.mysite.dto.condition;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 16:14
 * @description 评论的查找参数
 * 根据 状态查找，比如查找待审核的评论
 * 根据 时间查找
 * 根据 父级id查找，比如所对应的文章编号
 */
public class CommentCond implements Serializable {

    private String status;
    private int startTime;
    private int endTime;
    private int parent;

    public CommentCond(){}

    public CommentCond(String status, int startTime, int endTime, int parent) {
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.parent = parent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
