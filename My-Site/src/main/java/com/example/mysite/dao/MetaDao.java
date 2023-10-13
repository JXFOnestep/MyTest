package com.example.mysite.dao;

import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.condition.MetaCond;
import com.example.mysite.entity.Meta;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:36
 * @description
 */
@Mapper
public interface MetaDao {
    List<Meta> findAll();

    /**
     * 根据sql查询
     *
     * @param paraMap
     * @return
     */
    List<MetaDto> selectFromSql(Map<String, Object> paraMap);

    //根据类型获取meta数量
    Long getMetasCountByType(@Param("type") String type);

    /**
     * 根据条件查询
     *
     * @param metaCond
     * @return
     */
    List<Meta> getMetasByCond(MetaCond metaCond);

    /**
     * 根据编号获取项目
     *
     * @param mid
     * @return
     */
    Meta getMetaById(@Param("mid") Integer mid);


    /**
     * 添加项目
     *
     * @param meta
     * @return
     */
    int addMeta(Meta meta);


    /**
     * 删除项目
     *
     * @param mid
     * @return
     */
    int deleteMetaById(@Param("mid") Integer mid);

    /**
     * 更新项目
     *
     * @param meta
     * @return
     */
    int updateMeta(Meta meta);

    /**
     * 根据meta编号删除关联
     * @param mid
     * @return
     */
    int deleteRelationShipByMid(@Param("mid") Integer mid);


}
