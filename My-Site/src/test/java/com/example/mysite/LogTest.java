package com.example.mysite;

import com.example.mysite.entity.Log;
import com.example.mysite.service.log.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:26
 * @description
 */
public class LogTest extends BasicTest {
    @Autowired
    private LogService logService;

    @Test
    void testfindAll() {
        List<Log> all = logService.findAll();
        System.out.println(all);

    }
}
