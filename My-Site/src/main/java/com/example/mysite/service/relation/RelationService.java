package com.example.mysite.service.relation;

import com.example.mysite.entity.Relationships;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:19
 * @description
 */
public interface RelationService {
    List<Relationships> findAll();
}
