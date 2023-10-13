package com.example.mysite.controller;

import com.example.mysite.annotation.MyLog;
import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.constant.Types;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.condition.ContentCond;
import com.example.mysite.entity.Comment;
import com.example.mysite.entity.Content;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.comment.CommentService;
import com.example.mysite.service.content.ContentService;
import com.example.mysite.service.meta.MetaService;
import com.example.mysite.service.option.OptionService;
import com.example.mysite.utils.*;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:44
 * @description
 */
@Controller
public class HomeController {

    @Autowired
    private OptionService optionService;
    @Autowired
    private Commons commons;

    @Autowired
    private ContentService contentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MetaService metaService;


    @ApiOperation("作品主页")
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.index(1, limit, request);
    }

    @ApiOperation("作品主页-分页")
    @GetMapping(value = "/photo/page/{p}")
    public String index(
            @ApiParam(name = "page", value = "页数", required = false)
            @PathVariable(name = "p")
                    int page,
            @ApiParam(name = "limit", value = "条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "9999")
                    int limit,
            HttpServletRequest request
    ) {
        //如果page<0,则设为1，[0,MAX_PAGE]则为设置的值，大于MAX_PAGE则设为1
        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        ContentCond contentCond = new ContentCond();
        contentCond.setType(Types.PHOTO.getType()); //设置type 为 photo
        //查询t_contents中type为photo的作品，展示在博客首页
        PageInfo<Content> articles = contentService.getArticlesByCond(contentCond, page, limit);
        request.setAttribute("archives", articles);
        request.setAttribute("active", "work");
        return "site/index";
    }

    @ApiOperation("作品内容") //跳转到作品详情页
    @GetMapping(value = "/blog/article/{cid}")
    public String post(
            @ApiParam(name = "cid", value = "文章主键", required = true)
            @PathVariable("cid")
                    Integer cid,
            HttpServletRequest request
    ) {
        Content article = contentService.getArticleById(cid);
        //更新文章的点击量
//        this.updateArticleHit(article.getCid(),article.getHits());
        request.setAttribute("article", article);
        System.out.println("created:" + article.getCreated());
        System.out.println("Commons.fmtdate_en(article.getCreated()) = " + Commons.fmtdate_en(article.getCreated()));
        request.setAttribute("active", "blog");

        List<Comment> commentsByCId = commentService.getCommentsByCId(cid);
        request.setAttribute("comments",commentsByCId);

        return "site/blogs-details";
    }



    @ApiOperation("Blog首页")
    @MyLog
    @GetMapping(value = {"/blog", "/blog/index"})
    public String blogIndex(HttpServletRequest request,
                            @ApiParam(name = "limit", value = "页数", required = false)
                            @RequestParam(name = "limit", required = false, defaultValue = "11")
                                    int limit) {
        return this.blogIndex(request, 1, limit);
    }

    @ApiOperation("Blog首页-分页")
    @GetMapping(value = "/blog/page/{p}")
    public String blogIndex(HttpServletRequest request,
                            @PathVariable("p")
                                    int p,
                            @RequestParam(name = "limit", required = false, defaultValue = "11")
                                    int limit) {
        p = p < 0 || p > WebConstant.MAX_PAGE ? 1 : p;
        ContentCond contentCond = new ContentCond();
        contentCond.setType(Types.ARTICLE.getType());
        //获取分页的结果
        PageInfo<Content> articles = contentService.getArticlesByCond(contentCond, p, limit);
        request.setAttribute("articles", articles);//文章列表
        request.setAttribute("type", "articles");
        request.setAttribute("active", "blog");
        return "site/blog";
    }

    @ApiOperation("文章内容") //跳转到作品详情页
    @GetMapping(value = "/photo/article/{cid}")
    public String article(
            @PathVariable("cid")
                    Integer cid,
            HttpServletRequest request
    ) {
        Content article = contentService.getArticleById(cid);
        //更新文章的点击量
//        this.updateArticleHit(article.getCid(),article.getHits());
        request.setAttribute("archive", article);
        request.setAttribute("active", "work");
        return "site/works-details";
    }

    /**
     * 评论操作
     */
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    @MyLog
    public APIResponse comment(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(name = "cid", required = true) Integer cid,
                               @RequestParam(name = "coid", required = false) Integer coid,
                               @RequestParam(name = "author", required = false) String author,
                               @RequestParam(name = "mail", required = false) String mail,
                               @RequestParam(name = "url", required = false) String url,
                               @RequestParam(name = "text", required = true) String text,
                               @RequestParam(name = "_csrf_token", required = false) String _csrf_token) {

//        String ref = request.getHeader("Referer");
//        if (StringUtils.isBlank(ref) || StringUtils.isBlank(_csrf_token)) {
//            return APIResponse.fail("访问失败");
//        }

//        String token = cache.hget(Types.CSRF_TOKEN.getType(), _csrf_token);
//        if (StringUtils.isBlank(token)) {
//            return APIResponse.fail("访问失败");
//        }

        if (null == cid || StringUtils.isBlank(text)) {
            return APIResponse.fail("请输入完整后评论");
        }

        if (StringUtils.isNotBlank(author) && author.length() > 50) {
            return APIResponse.fail("姓名过长");
        }

        if (StringUtils.isNotBlank(mail) && !TaleUtils.isEmail(mail)) {
            return APIResponse.fail("请输入正确的邮箱格式");
        }

        if (StringUtils.isNotBlank(url) && !PatternKit.isURL(url)) {
            return APIResponse.fail("请输入正确的URL格式");
        }

        if (text.length() > 200) {
            return APIResponse.fail("请输入200个字符以内的评论");
        }

        String val = IPKit.getIpAddrByRequest(request) + ":" + cid;
//        Integer count = cache.hget(Types.COMMENTS_FREQUENCY.getType(), val);
//        if (null != count && count > 0) {
//            return APIResponse.fail("您发表评论太快了，请过会再试");
//        }

        author = TaleUtils.cleanXSS(author);
        text = TaleUtils.cleanXSS(text);

        author = EmojiParser.parseToAliases(author);
        text = EmojiParser.parseToAliases(text);

        Comment comments = new Comment();
        comments.setAuthor(author);
        comments.setCid(cid);
        comments.setIp(request.getRemoteAddr());
        comments.setUrl(url);
        comments.setContent(text);
        comments.setMail(mail);
        if(coid != null)
            comments.setParent(coid);
        try {
            commentService.addComment(comments);
//            cookie("tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
//            cookie("tale_remember_mail", URLEncoder.encode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);
//            if (StringUtils.isNotBlank(url)) {
//                cookie("tale_remember_url", URLEncoder.encode(url, "UTF-8"), 7 * 24 * 60 * 60, response);
//            }
//             设置对每个文章1分钟可以评论一次
//            cache.hset(Types.COMMENTS_FREQUENCY.getType(), val, 1, 60);

            return APIResponse.success();
        } catch (Exception e) {
            throw BusinessException.withErrorCode(ErrorConstant.Comment.ADD_NEW_COMMENT_FAIL);
        }
    }


    @ApiIgnore
    @GetMapping(value = {"/about", "/about/index"})
    public String getAbout(HttpServletRequest request) {
//        this.blogBaseData(request, null);//获取友链
        request.setAttribute("active", "about");

        //获取友情链接相关
        List<MetaDto> links = metaService.getMetaList(Types.LINK.getType(), null, WebConstant.MAX_POSTS);

        request.setAttribute("links", links);

        return "site/about";
    }

}
