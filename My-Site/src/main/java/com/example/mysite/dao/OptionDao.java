package com.example.mysite.dao;

import com.example.mysite.entity.Option;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:05
 * @description
 */
@Mapper
public interface OptionDao {
    List<Option> findAll();
    Option getOptionByName(@Param("name") String name);
}
