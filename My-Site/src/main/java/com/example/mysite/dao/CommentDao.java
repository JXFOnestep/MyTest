package com.example.mysite.dao;

import com.example.mysite.dto.condition.CommentCond;
import com.example.mysite.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 10:54
 * @description
 */
@Mapper
public interface CommentDao {
    List<Comment> findAll();

    List<Comment> getCommentsByCond(CommentCond commentCond);

    //只查询经过审核的评论
    List<Comment> getCommentsByArticleId(@Param("cid") int cid);

    Comment getCommentById(@Param("coid") int coid);

    Long getCommentsCount();

    //增
    int addComment(Comment comment);

    //删
    int deleteComment(@Param("coid") int coid);

    //修改评论状态
    int updateComment(@Param("coid") int coid, @Param("status") String status);
}
