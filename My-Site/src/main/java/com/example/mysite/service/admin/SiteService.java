package com.example.mysite.service.admin;

import com.example.mysite.dto.ArchiveDto;
import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.StatisticsDto;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Comment;
import com.example.mysite.entity.Content;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 16:01
 * @description
 */
public interface SiteService {

    //获取评论列表
    List<Comment> getComments(int limit);

    //获取最新的文章
    List<Content> getNewArticles(int limit);

    //获取单条评论
    Comment getComment(int coid);

    StatisticsDto getStatistics();

    //获取归档文件列表——只获取日期和数量
    List<ArchiveDto> getArchivesSimple(ContentCond contentCond);

    //获取归档文件列表——查询开始时间和结束时间内的文件
    List<ArchiveDto> getArchives(ContentCond contentCond);

    List<MetaDto> getMetas(String type, String orderBy, int limit);
}
