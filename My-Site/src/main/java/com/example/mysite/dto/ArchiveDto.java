package com.example.mysite.dto;

import com.example.mysite.entity.Content;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月26日 11:20
 * @description 文章归档类
 */
public class ArchiveDto implements Serializable {
    private String date;
    private String count;
    private List<Content> articles;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Content> getArticles() {
        return articles;
    }

    public void setArticles(List<Content> articles) {
        this.articles = articles;
    }

}
