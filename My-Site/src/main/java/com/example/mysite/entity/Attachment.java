package com.example.mysite.entity;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 21:49
 * @description 附件类 图片及其他文件
 */
public class Attachment implements Serializable {

    private static final long serialVersionUID = -7610804266227067185L;
    private int id;
    private String fname;
    private String ftype;
    private String fkey;
    private int authorId;
    private int created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", ftype='" + ftype + '\'' +
                ", fkey='" + fkey + '\'' +
                ", authorId=" + authorId +
                ", created=" + created +
                '}';
    }
}
