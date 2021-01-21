package com.xybh.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xybh.enums.Sex;
import com.xybh.mapper.UsersMapper;
import com.xybh.pojo.Users;
import com.xybh.pojo.bo.UserBO;
import com.xybh.service.UserService;
import com.xybh.utils.DateUtil;
import com.xybh.utils.EncryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 12:03 2021/1/21
 * @Modified:
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    public UsersMapper userMapper;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        userExample.createCriteria()
                .andEqualTo("username", username);
        Users result = userMapper.selectOneByExample(userExample);

        return result != null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Users createUser(UserBO userBO) {
        Users user = new Users();
        BeanUtils.copyProperties(userBO, user);
        user.setPassword(EncryptUtil.getInstance().Base64Encode(user.getPassword()));
        // 默认用户昵称同用户名
        user.setNickname(user.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate("2021-1-21"));
        // 默认性别 保密
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(user.getCreatedTime());
        //使用雪花算法
        user.setId(IdUtil.createSnowflake(0, 0).nextIdStr());

        userMapper.insert(user);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example example = new Example(Users.class);
        example.createCriteria()
                .andEqualTo("username", username)
                .andEqualTo("password", EncryptUtil.getInstance().Base64Encode(password));
        Users users = userMapper.selectOneByExample(example);

        return users;
    }
}
