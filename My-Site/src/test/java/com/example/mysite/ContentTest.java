package com.example.mysite;

import com.example.mysite.dao.ContentDao;
import com.example.mysite.dto.ArchiveDto;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Content;
import com.example.mysite.service.content.ContentService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:28
 * @description
 */
public class ContentTest extends BasicTest {

    @Autowired
    ContentService contentService;

    @Test
    public void testfindAll() {
        List<Content> allContent = contentService.findAll();
        System.out.println(allContent);
    }

    @Test
    public void testGetByType() {
        ContentCond contentCond = new ContentCond();
//        contentCond.setType(Types.PHOTO.getType()); //设置type 为 photo
        PageInfo<Content> articlesByCond = contentService.getArticlesByCond(contentCond, 1, 12);
        List<Content> list = articlesByCond.getList();
        System.out.println(list);
    }

    @Test
    public void testGetById() {
        Content articleById = contentService.getArticleById(35);
        System.out.println(articleById);

    }

    @Autowired
    private ContentDao contentDao;

    @Test
    public void testGetCount() {
        System.out.println(contentDao.getArticleCounts());
    }

    @Test
    public void testGetArchive() {
        ContentCond contentCond = new ContentCond();
        contentCond.setStartTime(1681912450);
//        contentCond.setEndTime(1682477161);
        List<ArchiveDto> archive = contentDao.getArchive(contentCond);
        for(ArchiveDto a : archive) {
            System.out.println(a.getCount() + " , " + a.getDate() + " , " + a.getArticles());
        }
    }

    @Test
    public void getByCId() {
        Content article = contentService.getArticleById(1);
        System.out.println(article);
        System.out.println(article.getCommentsNum());


    }


}
