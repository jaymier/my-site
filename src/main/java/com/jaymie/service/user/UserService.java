package com.jaymie.service.user;

import com.jaymie.model.UserDomain;

import org.springframework.stereotype.Service;
/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:40
 * @description : TODO
 */
@Service
public interface UserService {

    /**
     * @description : 更改用户信息
     * @author : jiangyuzhen
     * @date : 2019/11/21 16:36
     * @param user :
     * @return : int
     */
    int updateUserInfo(UserDomain user);

    /**
     * @description : 根据主键编号获取用户信息
     * @author : jiangyuzhen
     * @date : 2019/11/21 16:36
     * @param uId :
     * @return : com.jaymie.model.UserDomain
     */
    UserDomain getUserInfoById(Integer uId);


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    UserDomain login(String username, String password);

}
