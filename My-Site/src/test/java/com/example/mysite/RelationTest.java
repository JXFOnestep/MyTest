package com.example.mysite;

import com.example.mysite.entity.Relationships;
import com.example.mysite.service.relation.RelationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:20
 * @description
 */
public class RelationTest extends BasicTest{

    @Autowired
    private RelationService relationService;

    @Test
    void testRelation() {
        List<Relationships> all = relationService.findAll();

    }
}
