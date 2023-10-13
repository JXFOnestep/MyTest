package com.example.mysite.service.option.impl;

import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.dao.OptionDao;
import com.example.mysite.entity.Option;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.option.OptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:06
 * @description
 */
@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Override
//    @Cacheable(value = "optionCache",key = "'getAllOptions_'")
    public List<Option> getAllOptions() {
        return optionDao.findAll();
    }

    @Override
//    @Cacheable(value = "optionCache",key = "'getOptionByName_' + #p0")
    public Option getOptionByName(String name) {
        if(StringUtils.isBlank(name))
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return optionDao.getOptionByName(name);
    }
}
