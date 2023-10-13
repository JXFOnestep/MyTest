package com.example.mysite.controller.admin;

import com.example.mysite.annotation.MyLog;
import com.example.mysite.constant.Types;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.dto.ContentSearchDto;
import com.example.mysite.dto.MetaDto;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.content.ContentService;
import com.example.mysite.service.meta.MetaService;
import com.example.mysite.utils.APIResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年05月02日 15:44
 * @description
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private MetaService metaService;

    @ApiOperation("进入分类和标签页")
    @GetMapping("")
    public String index(HttpServletRequest request) {
        List<MetaDto> categories = metaService.getMetaList(Types.CATEGORY.getType(), null, WebConstant.MAX_POSTS);
        List<MetaDto> tags = metaService.getMetaList(Types.TAG.getType(), null, WebConstant.MAX_POSTS);
        request.setAttribute("categories", categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }

    @MyLog
    @ApiOperation("保存分类")
    @PostMapping("/save")
    @ResponseBody
    public APIResponse save(
            @ApiParam(name = "mid", value = "meta编号，主键自增", required = false)
            @RequestParam(name = "mid", required = false)
                    Integer mid,
            @ApiParam(name = "cname", value = "分类名", required = true)
            @RequestParam(name = "cname", required = true)
                    String cname
    ) {
        try {
            metaService.saveMeta(Types.CATEGORY.getType(), cname, mid);

        } catch (Exception e) {
            e.printStackTrace();
            String msg = "分类保存失败";
            if (e instanceof BusinessException) {
                BusinessException ex = (BusinessException) e;
                msg = ex.getErrorCode();
            }
//            LOGGER.error(msg, e);

            return APIResponse.fail(msg);
        }
        return APIResponse.success();
    }

    @MyLog
    @ApiOperation("删除分类")
    @PostMapping("/delete")
    @ResponseBody
    public APIResponse delete(
            @ApiParam(name = "mid", value = "meta id", required = true)
            @RequestParam(name = "mid", required = true)
                    Integer mid
    ) {
        try {
            metaService.deleteMetaById(mid);
        }catch (Exception e) {
            e.printStackTrace();
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }


    @Autowired
    private ContentService contentService;


    @ApiOperation("根据标签mid查询对应的所有文章")
    @PostMapping("/search")
    @MyLog
    @ResponseBody
    public APIResponse search(
            @ApiParam(name = "mid", required = true)
            @RequestParam(name = "mid", required = true)
                    Integer mid
    ) {
        List<ContentSearchDto> list = contentService.getArticleListByMid(mid);
        if (list == null)
            return APIResponse.fail("查找失败");
        return APIResponse.success(list);
    }

}
