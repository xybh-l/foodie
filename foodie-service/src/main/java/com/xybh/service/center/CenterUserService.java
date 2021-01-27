package com.xybh.service.center;

import com.xybh.pojo.Users;
import com.xybh.pojo.bo.center.CenterUserBO;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:25 2021/1/27
 * @Modified:
 */
public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId        用户id
     * @param centerUserBO  用户信息
     *
     * @return
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);
}
