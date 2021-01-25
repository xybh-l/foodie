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

    /**
     * 修改用户地址
     * @param addressBo 用户地址BO
     */
    void updateUserAddress(AddressBo addressBo);

    /**
     * 根据用户ID和地址ID删除对应的用户地址信息
     * @param userId    用户id
     * @param addressId 地址id
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 修改用户默认地址
     * @param userId    用户id
     * @param addressId 地址id
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

}
