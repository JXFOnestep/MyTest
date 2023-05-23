package com.example.mysite.service.content.impl;

import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.constant.Types;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.dao.ContentDao;
import com.example.mysite.dao.RelationshipsDao;
import com.example.mysite.dto.ContentSearchDto;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Content;
import com.example.mysite.entity.Relationships;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.content.ContentService;
import com.example.mysite.service.meta.MetaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:26
 * @description
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;


    @Autowired
    private MetaService metaService;

    @Autowired
    private RelationshipsDao relationshipsDao;

    @Override
    @Cacheable(value = "contentCache",key = "'findAll_'")
    public List<Content> findAll() {
        List<Content> res = new ArrayList<>();
        List<Content> all = contentDao.findAll();
        for(Content c : all) {
            c.setContent(c.getContent().substring(0,10)+"...");
            res.add(c);
        }
        return res;
    }

    @Override
    @Cacheable(value = "contentCache",key = "'getArticlesByCond_' + #p0")
    public PageInfo<Content> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize) {
        if (null == contentCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum, pageSize);
        List<Content> contents = contentDao.getArticlesByCond(contentCond);
        PageInfo<Content> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override //value是给整个缓存起的名字，key是在缓存区对应的key
    @Cacheable(value = "contentCache", key = "'atricleById_' + #p0")//开启缓存
    public Content getArticleById(int cid) {
        if (null == new Integer(cid))
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentDao.getArticleById(cid);
    }

    //allEntries=true表示清除所有缓存中的数据，如果不设置该属性，默认只清除与该方法对应的键值；
    @Override //beforeInvocation=true表示在方法执行前清除缓存，避免因为方法执行出错而导致缓存未被清除。
    @Transactional
    @CacheEvict(value={"contentCache","siteCache"},allEntries=true,beforeInvocation=true)
    public void addArticle(Content content) {
        if (null == content)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(content.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (content.getTitle().length() > WebConstant.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(content.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (content.getContent().length() > WebConstant.MAX_TEXT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);

        String tags = content.getTags();
        String categories = content.getCategories();
        contentDao.addArticle(content);

        int cid = content.getCid();
        metaService.addMetas(cid, tags, Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());
    }

    @Override
    @Transactional
    @CacheEvict(value={"contentCache","siteCache"},allEntries=true,beforeInvocation=true)
    public void updateArticleById(Content contentDomain) {
        //标签和分类
        String tags = contentDomain.getTags();
        String categories = contentDomain.getCategories();

        contentDao.updateArticleById(contentDomain);
        int cid = contentDomain.getCid();
        relationshipsDao.deleteRelationShipByCid(cid); //删除原来的对应关系
        metaService.addMetas(cid, tags, Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());
    }

    @Override
    @Transactional
    @CacheEvict(value={"contentCache","siteCache"},allEntries=true,beforeInvocation=true)
    public void deleteArticleById(Integer cid) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        contentDao.deleteArticleById(cid);
        //同时也要删除该文章下的所有评论
//        List<Comment> comments = commentDao.getCommentsByCId(cid);
//        if (null != comments && comments.size() > 0){
//            comments.forEach(comment ->{
//                commentDao.deleteComment(comment.getCoid());
//            });
//        }
        //删除标签和分类关联
        List<Relationships> relationShips = relationshipsDao.getRelationShipByCid(cid);
        if (null != relationShips && relationShips.size() > 0){
            relationshipsDao.deleteRelationShipByCid(cid);
        }

    }

    @Override
    @Transactional
    @CacheEvict(value={"contentCache","siteCache"},allEntries=true,beforeInvocation=true)
    public void updateCategory(String ordinal, String newCatefory) {
        ContentCond contentCond = new ContentCond();
        contentCond.setCategory(ordinal);
        List<Content> articlesByCond = contentDao.getArticlesByCond(contentCond);
        articlesByCond.forEach(article -> {
            article.setCategories(article.getCategories().replace(ordinal,newCatefory));
            contentDao.updateArticleById(article);
        });
    }

    @Override
    @Cacheable(value = "contentCache", key = "'getArticleByMid' + #p0")
    public List<ContentSearchDto> getArticleListByMid(Integer mid) {
        if(null == mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentDao.getArticleListByMid(mid);
    }
}
