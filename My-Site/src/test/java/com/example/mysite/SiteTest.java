package com.example.mysite;

import com.example.mysite.constant.Types;
import com.example.mysite.dto.ArchiveDto;
import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.StatisticsDto;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Comment;
import com.example.mysite.entity.Content;
import com.example.mysite.service.admin.SiteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 17:31
 * @description
 */
public class SiteTest extends BasicTest{

    @Autowired
    SiteService siteService;

    @Test
    public void getComments() {
        List<Comment> comments = siteService.getComments(2);
        System.out.println(comments);

//        PageHelper.startPage(1,limit);
//        List<Comment> commentList = commentDao.getCommentsByCond(new CommentCond());
    }

    @Test
    public void getCommentsById() {
        Comment comment = siteService.getComment(7);
        System.out.println(comment);
    }

    @Test
    public void getNewArticles() {
        List<Content> newArticles = siteService.getNewArticles(2);
        for(Content c : newArticles) {
            c.setContent(c.getContent().substring(0,3) + "...");
            System.out.println(c);
        }
        System.out.println("=========");
        List<Content> newArticles1 = siteService.getNewArticles(4);
        for(Content c : newArticles1) {
            c.setContent(c.getContent().substring(0,3) + "...");
            System.out.println(c);
        }
    }

    @Test
    public void getStatistics() {
        StatisticsDto statistics = siteService.getStatistics();
        System.out.println(statistics);
    }

    @Test
    public void getArchives() {
        ContentCond contentCond = new ContentCond();
        contentCond.setStartTime(1682391989);
        List<ArchiveDto> archivesSimple = siteService.getArchivesSimple(contentCond);
        for(ArchiveDto a : archivesSimple) {
            System.out.println(a.getCount() + " , " + a.getCount() + " , " + a.getArticles());
        }
        System.out.println("====================================");
        List<ArchiveDto> archiveDtos = siteService.getArchives(contentCond);
        for(ArchiveDto a : archiveDtos) {
            System.out.println(a.getCount() + " , " + a.getCount() + " , " + a.getArticles());
        }
    }

    @Test
    public void getMetas() {
        List<MetaDto> metas = siteService.getMetas(Types.TAG.getType(), "", 2);
        System.out.println(metas);
    }


}


