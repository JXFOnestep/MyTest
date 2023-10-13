package com.example.mysite.controller.admin;

import com.example.mysite.constant.Types;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.dto.AttAchDto;
import com.example.mysite.service.attachment.AttachService;
import com.example.mysite.utils.Commons;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xufeng Jiang
 * @date 2023年05月02日 21:46
 * @description
 */

@Controller
@RequestMapping("/admin/attach")
public class AttachController {

    @Autowired
    private AttachService attAchService;

    @ApiOperation("文件管理首页")
    @GetMapping(value = "")
    public String index(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
                    int page,
            @ApiParam(name = "limit", value = "条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "12")
                    int limit,
            HttpServletRequest request
    ){
        PageInfo<AttAchDto> atts = attAchService.getAtts(page, limit);
        request.setAttribute("attachs", atts);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", WebConstant.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }
}
