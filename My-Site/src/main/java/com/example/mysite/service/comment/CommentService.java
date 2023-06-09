package com.example.mysite.service.comment;

import com.example.mysite.dto.condition.CommentCond;
import com.example.mysite.entity.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 10:56
 * @description
 */
public interface CommentService {
    List<Comment> findAll();

    /**
     * 新增评论
     * @param commentDomain 评论的实体
     * @return
     */
    void addComment(Comment commentDomain);

    /**
     * 删除评论
     * @param coid 评论的主键编号
     * @return
     */
    void deleteComment(Integer coid);

    /**
     * 更新评论的状态
     */
    void updateCommentStatus(Integer coid, String status);


    /**
     * 查找单条评论
     * @param coid
     * @return
     */
    Comment getCommentById(Integer coid);

    /**
     * 根据文章编号获取评论列表--只显示通过审核的评论-正常状态的
     * @param cid 文章主键编号
     * @return
     */
    List<Comment> getCommentsByCId(Integer cid);

    /**
     * 根据条件获取评论列表
     * @param commentCond 查询条件
     * @param pageNum 分页参数 第几页
     * @param pageSize 分页参数 每页条数
     * @return
     */
    PageInfo<Comment> getCommentsByCond(CommentCond commentCond, int pageNum, int pageSize);


}
