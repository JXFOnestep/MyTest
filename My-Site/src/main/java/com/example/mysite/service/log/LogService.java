package com.example.mysite.service.log;

import com.example.mysite.entity.Log;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:24
 * @description
 */
public interface LogService {
    List<Log> findAll();

    PageInfo<Log> getLogs(int pageNum, int pageSize);

    void addLog(String action,String data,int authorId,String ip);

    void deleteLogById(int id);

}
