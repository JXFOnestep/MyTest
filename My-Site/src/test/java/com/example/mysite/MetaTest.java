package com.example.mysite;

import com.example.mysite.constant.Types;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.dao.MetaDao;
import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.condition.MetaCond;
import com.example.mysite.entity.Meta;
import com.example.mysite.service.meta.MetaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:07
 * @description
 */
public class MetaTest extends BasicTest{

    @Autowired
    private MetaService metaService;

    @Test
    void testAll() {
        List<Meta> all = metaService.findAll();
        System.out.println(all);
    }

    @Test
    void testFindBySQL() {
        List<MetaDto> metaList = metaService.getMetaList(Types.TAG.getType(), "", 2);
        System.out.println(metaList);
    }

    @Autowired
    private MetaDao metaDao;

    @Test
    void testCountsByType() {
        System.out.println(metaDao.getMetasCountByType(Types.ARTICLE.getType()));
        System.out.println(metaDao.getMetasCountByType(Types.PAGE.getType()));
        System.out.println(metaDao.getMetasCountByType(Types.PHOTO.getType()));
        System.out.println(metaDao.getMetasCountByType(Types.LINK.getType()));
        System.out.println(metaDao.getMetasCountByType(Types.TAG.getType()));
        System.out.println(metaDao.getMetasCountByType(Types.CATEGORY.getType()));
    }

    @Test
    void getMetasByCond() {
        MetaCond metaCond = new MetaCond();
        metaCond.setType(Types.TAG.getType());
        List<Meta> metasByCond = metaDao.getMetasByCond(metaCond);
        System.out.println(metasByCond);
    }

    @Test
    void getMetaList() {
        List<MetaDto> categories = metaService.getMetaList(Types.CATEGORY.getType(), null, WebConstant.MAX_POSTS);
        System.out.println(categories);
        System.out.println("================================");
        List<MetaDto> tags = metaService.getMetaList(Types.TAG.getType(), null, WebConstant.MAX_POSTS);
        System.out.println(tags);
    }
}
