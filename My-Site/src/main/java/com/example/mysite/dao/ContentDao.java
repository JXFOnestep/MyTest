package com.example.mysite.dao;

import com.example.mysite.dto.ArchiveDto;
import com.example.mysite.dto.ContentSearchDto;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:01
 * @description
 */
@Mapper
public interface ContentDao {
    List<Content> findAll();
    List<Content> getArticlesByCond(ContentCond contentCond);
    Content getArticleById(int cid);

    Long getArticleCounts();

    /**
     * 获取归档数据
     * @param contentCond 查询条件（只包含开始时间和结束时间）
     *                     同时限定  type = 'post' and status = 'publish'
     *                    即仅统计已经发表的博文
     * @return
     */
    List<ArchiveDto> getArchive(ContentCond contentCond);

    /**
     * 添加文章
     * @param content
     * @return
     */
    int addArticle(Content content);


    /**
     * 更新文章
     * @param content
     * @return
     */
    int updateArticleById(Content content);

    /**
     * 删除文章
     * @param cid
     * @return
     */
    int deleteArticleById(@Param("cid") int cid);

    /**
     * 根据所给的类别id，查询该类别的所有文章
     * @param mid
     * @return
     */
    List<ContentSearchDto> getArticleListByMid(@Param("mid") Integer mid);


}
