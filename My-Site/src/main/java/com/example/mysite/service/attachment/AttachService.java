package com.example.mysite.service.attachment;

import com.example.mysite.dto.AttAchDto;
import com.example.mysite.entity.Attachment;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:28
 * @description
 */
public interface AttachService {
    List<Attachment> findAll();

    /**
     * 获取所有的附件信息
     * @return
     */
    PageInfo<AttAchDto> getAtts(int pageNum, int pageSize);
}
