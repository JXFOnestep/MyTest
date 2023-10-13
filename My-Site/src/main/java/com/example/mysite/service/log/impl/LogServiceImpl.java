package com.example.mysite.service.log.impl;

import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.dao.LogDao;
import com.example.mysite.entity.Log;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.log.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:25
 * @description
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public List<Log> findAll() {
        return logDao.findAll();
    }

    @Override
    @Cacheable(value = "logCache", key = "'getLogs' + #p0")
    public PageInfo<Log> getLogs(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Log> logList = logDao.findAll();
        PageInfo<Log> pageInfo = new PageInfo<>(logList);
        return pageInfo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"logCache"},allEntries = true,beforeInvocation = true)
    public void addLog(String action, String data, int authorId, String ip) {
        Log log = new Log();
        log.setAction(action);
        log.setData(data);
        log.setAuthorId(authorId);
        log.setIp(ip);
        logDao.addLog(log);
    }

    @Override
    @Transactional
    @CacheEvict(value = "logCache",allEntries = true,beforeInvocation = true)
    public void deleteLogById(int id) {
        if (0 == id)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        logDao.deleteLogById(id);
    }
}
