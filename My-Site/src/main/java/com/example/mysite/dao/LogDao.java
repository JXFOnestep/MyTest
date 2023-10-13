package com.example.mysite.dao;

import com.example.mysite.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:23
 * @description
 */
@Mapper
public interface LogDao {
    List<Log> findAll();

    /**
     * 添加日志
     * @param log
     * @return
     */
    int addLog(Log log);

    /**
     * 删除日志
     * @param id
     * @return
     */
    int deleteLogById(@Param("id") Integer id);

    /**
     * 获取日志
     * @return
     */
    List<Log> getLogs();
}
