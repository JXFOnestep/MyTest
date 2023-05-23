package com.example.mysite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

/**
 * @author Xufeng Jiang
 * @date 2023年05月07日 10:29
 * @description
 */
public class CacheTest extends BasicTest{

    @Autowired
    CacheManager cacheManager;

    @Test
    void testCache() {
        System.out.println(cacheManager);
    }
}
