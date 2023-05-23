package com.example.mysite.dao;

import com.example.mysite.entity.Relationships;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:18
 * @description
 */
@Mapper
public interface RelationshipsDao {
    List<Relationships> findAll();

    /**
     * 获取数量
     * @param cid
     * @param mid
     * @return
     */
    Long getCountById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    /**
     * 添加
     * @param relationShip
     * @return
     */
    int addRelationShip(Relationships relationShip);

    /**
     * 根据文章编号删除关联
     * @param cid
     * @return
     */
    int deleteRelationShipByCid(@Param("cid") Integer cid);

    int deleteRelationShipByMid(@Param("mid") Integer mid);
    /**
     * 根据文章主键获取关联
     * @param cid
     * @return
     */
    List<Relationships> getRelationShipByCid(@Param("cid") Integer cid);

    List<Relationships> getRelationShipByMid(@Param("mid") Integer mid);

}
