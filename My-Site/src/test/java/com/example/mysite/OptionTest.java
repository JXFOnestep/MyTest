package com.example.mysite;

import com.example.mysite.entity.Option;
import com.example.mysite.service.option.OptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 15:08
 * @description
 */
public class OptionTest extends BasicTest{

    @Autowired
    OptionService optionService;

    @Test
    void testAll() {
        List<Option> all = optionService.getAllOptions();
        System.out.println(all);
    }
}
