package com.example.mysite.service.content;

import com.example.mysite.dto.ContentSearchDto;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Content;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:27
 * @description
 */
public interface ContentService {
    List<Content> findAll();
    PageInfo<Content> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize);
    Content getArticleById(int cid);
    void addArticle(Content content);
    /**
     * 更新文章
     * @param contentDomain
     * @return
     */
    void updateArticleById(Content contentDomain);

    /**
     * 根据编号删除文章
     * @param cid
     * @return
     */
    void deleteArticleById(Integer cid);


    /**
     * 更新分类
     * @param ordinal
     * @param newCatefory
     */
    void updateCategory(String ordinal, String newCatefory);


    List<ContentSearchDto> getArticleListByMid(Integer mid);

}
