package com.example.mysite.controller.admin;

import com.example.mysite.annotation.MyLog;
import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.dto.condition.CommentCond;
import com.example.mysite.entity.Comment;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.comment.CommentService;
import com.example.mysite.utils.APIResponse;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xufeng Jiang
 * @date 2023年04月28日 10:07
 * @description
 */
@Api("评论相关接口")
@Controller
@RequestMapping("/admin/comments")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ApiOperation("进入评论列表页")
    @GetMapping("")
    public String index(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page",required = false,defaultValue = "1")
            int page,
            @ApiParam(name = "limit",value = "每页条数",required = false)
            @RequestParam(name = "limit",required = false,defaultValue = "10")
            int limit,
            HttpServletRequest request) {
        PageInfo<Comment> commentsByCond = commentService.getCommentsByCond(new CommentCond(), page, limit);
        request.setAttribute("comments",commentsByCond);
        return "admin/comment_list";
    }

    @ApiOperation("删除一条评论")
    @PostMapping("/delete")
    @ResponseBody
    @MyLog
    public APIResponse delete(
            @ApiParam(name = "coid",value = "评论id",required = true)
            @RequestParam(name = "coid",required = true)
            Integer coid) {
        try {
            Comment comment = commentService.getCommentById(coid);
            if (null == comment)
                throw BusinessException.withErrorCode(ErrorConstant.Comment.COMMENT_NOT_EXIST);

            commentService.deleteComment(coid);
        } catch (Exception e) {
            e.printStackTrace();
//            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }

    @ApiOperation("修改评论状态")
    @PostMapping("/status")
    @ResponseBody
    @MyLog
    public APIResponse status(
            @ApiParam(name = "coid",value = "评论id",required = true)
            @RequestParam(name = "coid",required = true)
                    Integer coid,
            @ApiParam(name = "status", value = "状态", required = true)
            @RequestParam(name = "status", required = true)
                    String status
    ) {
        try {
            Comment commentById = commentService.getCommentById(coid);
            if(commentById != null) {
                commentService.updateCommentStatus(coid,status);
            }else {
                return APIResponse.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }


}
