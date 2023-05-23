package com.example.mysite;

import com.example.mysite.dao.AttachDao;
import com.example.mysite.entity.Attachment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月27日 19:57
 * @description
 */
public class AttachTest extends BasicTest{

    @Autowired
    AttachDao attachDao;

    @Test
    void testAttach() {
        List<Attachment> all = attachDao.findAll();
        System.out.println(all);
        System.out.println(attachDao.getAttachCounts());
    }

}
