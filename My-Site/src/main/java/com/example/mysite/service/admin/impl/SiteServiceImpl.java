package com.example.mysite.service.admin.impl;

import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.constant.Types;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.dao.AttachDao;
import com.example.mysite.dao.CommentDao;
import com.example.mysite.dao.ContentDao;
import com.example.mysite.dao.MetaDao;
import com.example.mysite.dto.ArchiveDto;
import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.StatisticsDto;
import com.example.mysite.dto.condition.CommentCond;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Comment;
import com.example.mysite.entity.Content;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.admin.SiteService;
import com.example.mysite.utils.DateKit;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 16:02
 * @description
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private MetaDao metaDao;

    @Autowired
    private AttachDao attachDao;


    @Override
    @Cacheable(value = "siteCache", key = "'comments_' + #p0")
    public List<Comment> getComments(int limit) {
        if(limit < 0 || limit > 10) {
            limit = 10;
        }
        PageHelper.startPage(0,limit); //pageNum默认从1开始，不是从0开始
        List<Comment> commentList = commentDao.getCommentsByCond(new CommentCond());
        return commentList;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'newArticles_' + #p0")
    public List<Content> getNewArticles(int limit) {
        if(limit < 0 || limit > 10) {
            limit = 10;
        }
        PageHelper.startPage(1,limit);
        //这里传入空的ContentCond，其实就是按照时间倒序，查询limit条数据
        List<Content> res = contentDao.getArticlesByCond(new ContentCond());
        return res;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'comment_' + #p0")
    public Comment getComment(int coid) {
        if(coid <= 0)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        Comment commentById = commentDao.getCommentById(coid);
        return commentById;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'statistics_'")
    public StatisticsDto getStatistics() {
        //文章数、评论数、连接数、附件数
        Long articleCounts = contentDao.getArticleCounts();
        Long commentsCount = commentDao.getCommentsCount();
        Long links = metaDao.getMetasCountByType(Types.LINK.getType());
        Long attachCounts = attachDao.getAttachCounts();
        StatisticsDto statisticsDto = new StatisticsDto(articleCounts,commentsCount,links,attachCounts);
        return statisticsDto;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'archivesSimple_' + #p0")
    public List<ArchiveDto> getArchivesSimple(ContentCond contentCond) {
        //查询条件（只包含开始时间和结束时间）,可以用于统计每个月发表的博文数
        List<ArchiveDto> archives = contentDao.getArchive(contentCond);
        return archives;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'archives_' + #p0")
    public List<ArchiveDto> getArchives(ContentCond contentCond) {
        List<ArchiveDto> archives = contentDao.getArchive(contentCond);
        parseArchives(archives, contentCond); //上面的方法只统计每个月的博文数，没有获取该时间段内的博文
        //这个方法则会获取这个时间段内的博文
        return archives;
    }

    private void parseArchives(List<ArchiveDto> archives, ContentCond contentCond) {
        if (null != archives){
            archives.forEach(archive -> { //遍历每个月内的文章数
                String date = archive.getDate();
                Date sd = DateKit.dateFormat(date, "yyyy年MM月");
                int start = DateKit.getUnixTimeByDate(sd);
                int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_MONTH, sd, 1)) - 1;
                ContentCond cond = new ContentCond();
                cond.setStartTime(start);
                cond.setEndTime(end);
                cond.setType(contentCond.getType());
                List<Content> contentss = contentDao.getArticlesByCond(cond); //查询在这个时间段内的内容
                archive.setArticles(contentss);
            });
        }
    }

    @Override
    @Cacheable(value = "siteCache", key = "'metas_' + #p0")
    public List<MetaDto> getMetas(String type, String orderBy, int limit) {

        List<MetaDto> retList=null;
        if (StringUtils.isNotBlank(type)) {
            if(StringUtils.isBlank(orderBy)){
                orderBy = "count desc, a.mid desc";
            }
            if(limit < 1 || limit > WebConstant.MAX_POSTS){
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderBy);
            paraMap.put("limit", limit);
            retList= metaDao.selectFromSql(paraMap);
        }
        return retList;
    }
}
