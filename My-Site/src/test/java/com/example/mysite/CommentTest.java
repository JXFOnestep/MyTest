package com.example.mysite;

import com.example.mysite.dao.CommentDao;
import com.example.mysite.dto.condition.CommentCond;
import com.example.mysite.entity.Comment;
import com.example.mysite.service.comment.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:28
 * @description
 */
public class CommentTest extends BasicTest {

    @Autowired
    CommentService commentService;

    @Test
    public void testfindAll() {
        List<Comment> allComment = commentService.findAll();
        System.out.println(allComment);
    }

    @Autowired
    CommentDao commentDao;

    @Test
    public void testGetByCond() {
        CommentCond commentCond = new CommentCond();
        commentCond.setStatus("approved");
        List<Comment> commentsByCond = commentDao.getCommentsByCond(commentCond);
        System.out.println(commentsByCond);
    }

    @Test
    public void testGetById() {
        Comment commentById = commentDao.getCommentById(0);
        System.out.println(commentById);
    }

    @Test
    public void testGetCounts() {
        System.out.println(commentDao.getCommentsCount());
    }

    @Test
    public void getByCid() {
        List<Comment> commentsByArticleId = commentDao.getCommentsByArticleId(33);
        System.out.println(commentsByArticleId);
    }


}
