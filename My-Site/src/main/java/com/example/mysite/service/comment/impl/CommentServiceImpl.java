package com.example.mysite.service.comment.impl;

import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.dao.CommentDao;
import com.example.mysite.dto.condition.CommentCond;
import com.example.mysite.entity.Comment;
import com.example.mysite.entity.Content;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.comment.CommentService;
import com.example.mysite.service.content.ContentService;
import com.example.mysite.utils.DateKit;
import com.example.mysite.utils.TaleUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 10:57
 * @description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentService contentService;

    private static final Map<String, String> STATUS_MAP = new ConcurrentHashMap<>();

    /**
     * 评论状态：正常
     */
    private static final String STATUS_NORMAL = "approved";
    /**
     * 评论状态：待审核， 不显示
     */
    private static final String STATUS_BLANK = "not_audit";

    static {
        STATUS_MAP.put("approved", STATUS_NORMAL);
        STATUS_MAP.put("not_audit", STATUS_BLANK);
    }


    @Override
    @Cacheable(value = "commentCaches", key = "'findAll_'")
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override //@CacheEvict删除缓存数据，执行顺序是先进行方法调用，然后将缓存进行清除
    @CacheEvict(value={"commentCaches","siteCache"},allEntries=true)//allEntries=true表示清除所有缓存中的数据，如果不设置该属性，默认只清除与该方法对应的键值；
    public void addComment(Comment comments) {
        String msg = null;
        if (null == comments) {
            msg = "评论对象为空";
        } else {
            if (StringUtils.isBlank(comments.getAuthor())) {
                comments.setAuthor("热心网友");
            }
            if (StringUtils.isNotBlank(comments.getMail()) && !TaleUtils.isEmail(comments.getMail())) {
                msg = "请输入正确的邮箱格式";
            }
            if (StringUtils.isBlank(comments.getContent())) {
                msg = "评论内容不能为空";
            }
            if (comments.getContent().length() < 5 || comments.getContent().length() > 2000) {
                msg = "评论字数在5-2000个字符";
            }
            if (0 == comments.getCid()) {
                msg = "评论文章不能为空";
            }
            if (msg != null)
                throw BusinessException.withErrorCode(msg);
            //验证给定的文章id是否存在
            Content article = contentService.getArticleById(comments.getCid());
            if (article == null)
                throw BusinessException.withErrorCode("该文章不存在");
            comments.setOwnerId(article.getAuthorId());
            comments.setStatus(STATUS_MAP.get(STATUS_BLANK));//评论的初始状态为待审核
            comments.setCreated(DateKit.getCurrentUnixTime());
            commentDao.addComment(comments);
            System.out.println("addComment " + comments);

            //更新文章的评论数
            article.setCommentsNum(article.getCommentsNum() + 1);
            contentService.updateArticleById(article);
        }
    }

    @Override
    @CacheEvict(value = {"commentCaches","siteCache"}, allEntries = true)
    public void deleteComment(Integer coid) {
        if (coid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        // 如果删除的评论存在子评论，一并删除
        //查找当前评论是否有子评论
        CommentCond commentCond = new CommentCond();
        commentCond.setParent(coid);
        Comment comment = commentDao.getCommentById(coid);
        List<Comment> childComments = commentDao.getCommentsByCond(commentCond);
        Integer count = 0;
        //删除子评论
        if (null != childComments && childComments.size() > 0) {
            for (int i = 0; i < childComments.size(); i++) {
                commentDao.deleteComment(childComments.get(i).getCoid());
                count++;
            }
        }

        //删除当前评论
        commentDao.deleteComment(coid);
        count++;

        Content article = contentService.getArticleById(comment.getCid());

        //更新文章的评论数
        if (null != article
                && article.getCommentsNum() != 0) {
            article.setCommentsNum(article.getCommentsNum() - count);
            contentService.updateArticleById(article);
        }

    }

    @Override
    @CacheEvict(value = {"commentCaches","siteCache"}, allEntries = true)
    public void updateCommentStatus(Integer coid, String status) {
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        commentDao.updateComment(coid,status);
    }

    @Override
    @Cacheable(value = "commentCaches", key = "'commentById_' + #p0") //p0表示第一个参数
    public Comment getCommentById(Integer coid) {
        if (coid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return commentDao.getCommentById(coid);
    }

    @Override
    @Cacheable(value = "commentCaches", key = "'commentByCId_' + #p0") //p0表示第一个参数
    public List<Comment> getCommentsByCId(Integer cid) {
        if (cid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        //查询某篇文章的所有评论
        return commentDao.getCommentsByArticleId(cid);
    }

    @Override
    @Cacheable(value = "commentCaches", key = "'commentsByCond_' + #p0")
    public PageInfo<Comment> getCommentsByCond(CommentCond commentCond, int pageNum, int pageSize) {
        System.out.println("执行getCommentsByCond");
        if (commentCond == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentsByCond = commentDao.getCommentsByCond(commentCond);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentsByCond);
        return pageInfo;
    }


}
