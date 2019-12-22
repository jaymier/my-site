package com.jaymie.utils;

import com.google.gson.Gson;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:40
 * @description : json转换工具
 */
public class GsonUtils {

    private static final Gson gson = new Gson();

    public static String toJsonString(Object object){
      return object==null?null:gson.toJson(object);
    }
}
