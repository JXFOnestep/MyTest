package com.example.mysite.service.attachment.impl;

import com.example.mysite.dao.AttachDao;
import com.example.mysite.dto.AttAchDto;
import com.example.mysite.entity.Attachment;
import com.example.mysite.service.attachment.AttachService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:28
 * @description
 */

@Service
public class AttachServiceImpl implements AttachService {

    @Autowired
    private AttachDao attachDao;

    @Override
    public List<Attachment> findAll() {
        return attachDao.findAll();
    }

    @Override
    @Cacheable(value = "attCaches", key = "'atts' + #p0")
    public PageInfo<AttAchDto> getAtts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttAchDto> atts = attachDao.getAtts();
        PageInfo<AttAchDto> pageInfo = new PageInfo<>(atts);
        return pageInfo;
    }
}
