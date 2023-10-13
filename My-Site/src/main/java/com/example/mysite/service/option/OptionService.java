package com.example.mysite.service.option;

import com.example.mysite.entity.Option;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:06
 * @description
 */
public interface OptionService {

    List<Option> getAllOptions();
    Option getOptionByName(String name);

}
