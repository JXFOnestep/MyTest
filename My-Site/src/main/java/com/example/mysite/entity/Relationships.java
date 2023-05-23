package com.example.mysite.entity;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 21:54
 * @description 博文关联信息类
 */
public class Relationships implements Serializable {

    private static final long serialVersionUID = -172033025153061207L;
    private int cid; //文章id
    private int mid; //项目编号

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "Relationships{" +
                "cid=" + cid +
                ", mid=" + mid +
                '}';
    }
}
