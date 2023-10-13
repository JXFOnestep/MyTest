package com.example.mysite.dao;

import com.example.mysite.dto.AttAchDto;
import com.example.mysite.entity.Attachment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:26
 * @description
 */
@Mapper
public interface AttachDao {
    List<Attachment> findAll();
    Long getAttachCounts();

    /**
     * 获取所有的附件信息
     * @return
     */
    List<AttAchDto> getAtts();
}
