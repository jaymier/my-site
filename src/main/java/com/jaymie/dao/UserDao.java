package com.jaymie.dao;

import com.jaymie.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ：jiangyuzhen
 * @date ：2019/11/20 10:29
 * @description : TODO
 */
@Mapper
public interface UserDao {

    /**
     * 更改用户信息
     * @author : jiangyuzhen
     * @date : 2019/11/21 16:35
     * @param user :
     * @return : int
     */
    int updateUserInfo(UserDomain user);

    /**
     * 根据主键编号获取用户信息
     * @author : jiangyuzhen
     * @date : 2019/11/21 16:35
     * @param uId :
     * @return : int
     */
    UserDomain getUserInfoById(@Param("uid") Integer uId);

    /**
     * 根据用户名和密码获取用户信息
     * @param username
     * @param password
     * @return
     */
    UserDomain getUserInfoByCond(@Param("username") String username, @Param("password") String password);

}
