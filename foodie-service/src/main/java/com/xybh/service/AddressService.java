package com.xybh.service;

import com.xybh.pojo.UserAddress;
import com.xybh.pojo.bo.AddressBo;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 19:26 2021/1/23
 * @Modified:
 */
public interface AddressService {

    /**
     * 根据用户id查询地址列表
     * @param userId 用户id
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 新增用户地址
     * @param address 用户地址BO
     */
    void addNewUserAddress(AddressBo address);
}
