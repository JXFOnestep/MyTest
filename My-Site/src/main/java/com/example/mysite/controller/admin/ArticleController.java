package com.example.mysite.controller.admin;

import com.example.mysite.annotation.MyLog;
import com.example.mysite.constant.LogActions;
import com.example.mysite.constant.Types;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.dto.condition.MetaCond;
import com.example.mysite.entity.Content;
import com.example.mysite.entity.Meta;
import com.example.mysite.service.content.ContentService;
import com.example.mysite.service.log.LogService;
import com.example.mysite.service.meta.MetaService;
import com.example.mysite.utils.APIResponse;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月28日 9:48
 * @description
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private MetaService metaService;

    @Autowired
    private LogService logService;

    @ApiOperation("文章页")
    @GetMapping("")
    @MyLog
    public String index(HttpServletRequest request,
                        @ApiParam(name = "page", value = "页数", required = false)
                        @RequestParam(name = "page", required = false, defaultValue = "1")
                                int page,
                        @ApiParam(name = "limit", value = "每页数量", required = false)
                            @RequestParam(name = "limit", required = false, defaultValue = "10")
                                    int limit) {
        //查询所有文章
        PageInfo<Content> list = contentService.getArticlesByCond(new ContentCond(), page, limit);
        request.setAttribute("articles", list);
        return "admin/article_list";
    }


    @ApiOperation("文章发布页")
    @GetMapping("/publish")
    public String toPublish(HttpServletRequest request) {
        MetaCond metaCond = new MetaCond();
        metaCond.setType(Types.CATEGORY.getType());
        List<Meta> metas = metaService.getMetas(metaCond);
        request.setAttribute("categories", metas);
        return "admin/article_publish";
    }


    @MyLog
    @ApiOperation("发布文章")
    @PostMapping("/publish")
    @ResponseBody
    public APIResponse publish(
            HttpServletRequest request,
            @ApiParam(name = "title", value = "标题", required = true)
            @RequestParam(name = "title", required = true)
                    String title,
            @ApiParam(name = "titlePic", value = "标题图片", required = false)
            @RequestParam(name = "titlePic", required = false)
                    String titlePic,
            @ApiParam(name = "slug", value = "内容缩略名", required = false)
            @RequestParam(name = "slug", required = false)
                    String slug,
            @ApiParam(name = "content", value = "内容", required = true)
            @RequestParam(name = "content", required = true)
                    String content,
            @ApiParam(name = "type", value = "文章类型", required = true)
            @RequestParam(name = "type", required = true)
                    String type,
            @ApiParam(name = "status", value = "文章状态", required = true)
            @RequestParam(name = "status", required = true)
                    String status,
            @ApiParam(name = "tags", value = "标签", required = false)
            @RequestParam(name = "tags", required = false)
                    String tags,
            @ApiParam(name = "categories", value = "分类", required = false)
            @RequestParam(name = "categories", required = false, defaultValue = "默认分类")
                    String categories,
            @ApiParam(name = "allowComment", value = "是否允许评论", required = true)
            @RequestParam(name = "allowComment", required = true)
                    Boolean allowComment
    ) {
        Content article = new Content();
        article.setTitle(title);
        article.setTitlePic(titlePic);
        article.setSlug(slug);
        article.setContent(content);
        article.setType(type);
        article.setTags(type.equals(Types.ARTICLE.getType()) ? tags : null);
        article.setStatus(status);
        //只允许博客文章有分类，防止作品被收入分类
        article.setCategories(type.equals(Types.ARTICLE.getType()) ? categories : null);
        article.setAllowComment(allowComment ? 1 : 0);
        contentService.addArticle(article);


        return APIResponse.success(); //如果addArticle没有抛出异常，那么新增成功，由前端ajax进行跳转
    }


    @ApiOperation("编辑文章")
    @GetMapping("/{cid}")
    public String edit(
            HttpServletRequest request,
            @PathVariable("cid") int cid) {
        //根据cid查询文章信息，传回前端
        Content article = contentService.getArticleById(cid);
        MetaCond metaCond = new MetaCond();
        metaCond.setType(Types.CATEGORY.getType());
        List<Meta> metas = metaService.getMetas(metaCond);
        request.setAttribute("categories", metas);
        request.setAttribute("contents", article);

        /**
         *  Exception evaluating SpringEL expression:
         *  "null !=contents and
         *  adminCommons.exist_cat(c, contents.categories)"
         *  (template: "admin/article_publish" - line 100, col 53)
         */

        return "admin/article_publish";
    }

    @ApiOperation("编辑保存文章")
    @PostMapping("/modify")
    @ResponseBody
    @MyLog
    public APIResponse modifyArticle(
            HttpServletRequest request,
            @ApiParam(name = "cid", value = "文章主键", required = true)
            @RequestParam(name = "cid", required = true)
                    Integer cid,
            @ApiParam(name = "title", value = "标题", required = true)
            @RequestParam(name = "title", required = true)
                    String title,
            @ApiParam(name = "titlePic", value = "标题图片", required = false)
            @RequestParam(name = "titlePic", required = false)
                    String titlePic,
            @ApiParam(name = "slug", value = "内容缩略名", required = false)
            @RequestParam(name = "slug", required = false)
                    String slug,
            @ApiParam(name = "content", value = "内容", required = true)
            @RequestParam(name = "content", required = true)
                    String content,
            @ApiParam(name = "type", value = "文章类型", required = true)
            @RequestParam(name = "type", required = true)
                    String type,
            @ApiParam(name = "status", value = "文章状态", required = true)
            @RequestParam(name = "status", required = true)
                    String status,
            @ApiParam(name = "tags", value = "标签", required = false)
            @RequestParam(name = "tags", required = false)
                    String tags,
            @ApiParam(name = "categories", value = "分类", required = false)
            @RequestParam(name = "categories", required = false, defaultValue = "默认分类")
                    String categories,
            @ApiParam(name = "allowComment", value = "是否允许评论", required = true)
            @RequestParam(name = "allowComment", required = true)
                    Boolean allowComment
    ) {
        Content contentEntity = new Content();
        contentEntity.setCid(cid);
        contentEntity.setTitle(title);
        contentEntity.setTitlePic(titlePic);
        contentEntity.setSlug(slug);
        contentEntity.setContent(content);
        contentEntity.setType(type);
        contentEntity.setStatus(status);
        contentEntity.setTags(tags);
        contentEntity.setCategories(categories);
        contentEntity.setAllowComment(allowComment ? 1 : 0);

        contentService.updateArticleById(contentEntity);
        return APIResponse.success();
    }

    @ApiOperation("删除文章")
    @PostMapping("/delete")
    @ResponseBody
    @MyLog
    public APIResponse deleteArticle(
            @ApiParam(name = "cid", value = "文章主键", required = true)
            @RequestParam(name = "cid", required = true)
                    Integer cid,
            HttpServletRequest request) {
        contentService.deleteArticleById(cid);                      //authorID TODO
        logService.addLog(LogActions.DEL_ARTICLE.getAction(), cid + "", /*this.getUid(request)*/0, request.getRemoteAddr());
        return APIResponse.success();
    }

    @ApiOperation("根据文章cid查找文章标题")
    @PostMapping("/getByCoid")
    @ResponseBody
    public APIResponse getByCoid(
            @ApiParam(name = "cid", value = "文章主键", required = true)
            @RequestParam(name = "cid", required = true)
                    Integer cid
    ) {
        Content article = contentService.getArticleById(cid);
        if(article == null) {
            return APIResponse.fail("查找失败");
        }

        return APIResponse.success(article.getTitle());
    }


}
