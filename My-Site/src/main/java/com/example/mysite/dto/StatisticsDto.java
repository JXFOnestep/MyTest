package com.example.mysite.dto;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 15:54
 * @description 后台统计对象
 */
public class StatisticsDto implements Serializable {

    //文章数
    private Long articles;

    //评论数
    private Long comments;

    //连接数
    private Long links;

    //附件数
    private Long attaches;

    public StatisticsDto(){}

    public StatisticsDto(Long articles, Long comments, Long links, Long attaches) {
        this.articles = articles;
        this.comments = comments;
        this.links = links;
        this.attaches = attaches;
    }

    public Long getArticles() {
        return articles;
    }

    public void setArticles(Long articles) {
        this.articles = articles;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getLinks() {
        return links;
    }

    public void setLinks(Long links) {
        this.links = links;
    }

    public Long getAttaches() {
        return attaches;
    }

    public void setAttaches(Long attaches) {
        this.attaches = attaches;
    }

    @Override
    public String toString() {
        return "StatisticsDto{" +
                "articles=" + articles +
                ", comments=" + comments +
                ", links=" + links +
                ", attaches=" + attaches +
                '}';
    }
}
