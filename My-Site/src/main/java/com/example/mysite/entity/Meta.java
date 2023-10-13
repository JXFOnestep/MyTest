package com.example.mysite.entity;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 21:55
 * @description 项目类
 */
public class Meta implements Serializable {

    private static final long serialVersionUID = -5410238484066665465L;
    private int mid;
    private String name;
    private String slug;
    private String type;
    private String contentType;
    private String description;
    private int sort;
    private int parent;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "mid=" + mid +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", type='" + type + '\'' +
                ", contentType='" + contentType + '\'' +
                ", description='" + description + '\'' +
                ", sort=" + sort +
                ", parent=" + parent +
                '}';
    }
}
