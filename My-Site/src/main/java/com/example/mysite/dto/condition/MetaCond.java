package com.example.mysite.dto.condition;

import java.io.Serializable;

/**
 * @author Xufeng Jiang
 * @date 2023年04月28日 10:41
 * @description
 */
public class MetaCond implements Serializable {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
