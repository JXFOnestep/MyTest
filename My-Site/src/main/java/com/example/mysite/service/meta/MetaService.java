package com.example.mysite.service.meta;

import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.condition.MetaCond;
import com.example.mysite.entity.Meta;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:37
 * @description
 */
public interface MetaService {
    List<Meta> findAll();

    /**
     * 根据类型查询项目列表，带项目下面的文章数
     * @param type
     * @param orderby
     * @param limit
     * @return
     */
    List<MetaDto> getMetaList(String type, String orderby, int limit);

    /**
     * 获取所有的项目
     * @param metaCond 查询条件
     * @return
     */
    List<Meta> getMetas(MetaCond metaCond);

    /**
     * 批量添加
     * @param cid
     * @param names
     * @param type
     */
    void addMetas(Integer cid, String names, String type);

    /**
     * 添加或者更新
     * @param cid
     * @param name
     * @param type
     */
    void saveOrUpdate(Integer cid, String name, String type);

    /**
     * 添加项目
     * @param meta
     * @return
     */
    void addMeta(Meta meta);

    /**
     * 添加
     * @param type
     * @param name
     * @param mid
     */
    void saveMeta(String type, String name, Integer mid);


    /**
     * 删除项目
     * @param mid
     * @return
     */
    void deleteMetaById(Integer mid);

    /**
     * 更新项目
     * @param meta
     * @return
     */
    void updateMeta(Meta meta);
}
