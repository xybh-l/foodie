package com.xybh.service;

import com.xybh.pojo.Users;
import com.xybh.pojo.bo.UserBO;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 12:02 2021/1/21
 * @Modified:
 */
public interface UserService {
    /**
     * 判断用户名是否重复
     * @param username  用户名
     * @return  是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 注册用户名
     * @param userBO 前端传输过来的数据
     * @return
     */
    Users createUser(UserBO userBO);

    /**
     * 检索用户名和密码是否匹配
     * @param username  用户名
     * @param password  密码
     * @return
     */
    Users queryUserForLogin(String username, String password);
}
