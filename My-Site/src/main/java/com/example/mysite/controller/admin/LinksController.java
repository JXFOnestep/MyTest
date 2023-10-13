package com.example.mysite.controller.admin;

import com.example.mysite.constant.Types;
import com.example.mysite.dto.condition.MetaCond;
import com.example.mysite.entity.Meta;
import com.example.mysite.service.meta.MetaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年05月02日 22:00
 * @description
 */
@Controller
@RequestMapping("/admin/links")
public class LinksController {

    @Autowired
    private MetaService metaService;

    @ApiOperation("友链页面")
    @GetMapping(value = "")
    public String index(HttpServletRequest request) {

        MetaCond metaCond = new MetaCond();
        metaCond.setType(Types.LINK.getType());
        List<Meta> metas = metaService.getMetas(metaCond);
        request.setAttribute("links", metas);
        return "admin/links";
    }
}
