package com.example.mysite.dto;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年05月02日 16:44
 * @description 文章查找的实体类，只保留少数字段
 *
 */
public class ContentSearchDto implements Serializable {
    private int cid;
    private String title;
    private String tags;
    private String categories;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ContentSearchDto{" +
                "cid=" + cid +
                ", title='" + title + '\'' +
                ", tags='" + tags + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }
}
