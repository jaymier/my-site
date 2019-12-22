package com.jaymie.dto;

import com.jaymie.model.MetaDomain;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:28
 * @description : 标签、分类列表
 */
public class MetaDto extends MetaDomain {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
