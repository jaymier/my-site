package com.jaymie.dto;

import com.jaymie.model.ContentDomain;

import java.util.List;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:28
 * @description : 文章归档类
 */
public class ArchiveDto {

    private String date;
    private String count;
    private List<ContentDomain> articles;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ContentDomain> getArticles() {
        return articles;
    }

    public void setArticles(List<ContentDomain> articles) {
        this.articles = articles;
    }
}
