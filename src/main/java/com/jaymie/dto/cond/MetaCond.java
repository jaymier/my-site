package com.jaymie.dto.cond;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:29
 * @description : meta查询条件
 */
public class MetaCond {

    /**
     * meta Name
     */
    private String name;
    /**
     * 类型
     */
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
