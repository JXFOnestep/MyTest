package com.example.mysite.service.relation.impl;

import com.example.mysite.dao.RelationshipsDao;
import com.example.mysite.entity.Relationships;
import com.example.mysite.service.relation.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:19
 * @description
 */

@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private RelationshipsDao relationshipsDao;

    @Override
    @Cacheable(value = "relationCache",key = "'findAll_'")
    public List<Relationships> findAll() {
        return relationshipsDao.findAll();
    }
}
