package com.jaymie.dto.cond;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:29
 * @description : 用户查找条件
 */
public class UserCond {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
