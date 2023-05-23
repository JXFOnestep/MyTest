package com.example.mysite.dto;

import com.example.mysite.entity.Meta;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月26日 16:41
 * @description
 */
public class MetaDto extends Meta {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

