package com.xybh.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xybh.enums.YesOrNo;
import com.xybh.mapper.CarouselMapper;
import com.xybh.mapper.UserAddressMapper;
import com.xybh.pojo.Carousel;
import com.xybh.pojo.UserAddress;
import com.xybh.pojo.bo.AddressBo;
import com.xybh.service.AddressService;
import com.xybh.service.CarouselService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @author a1353
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private UserAddressMapper addressMapper;


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);

        return addressMapper.select(ua);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void addNewUserAddress(AddressBo address) {
        // 1.判断当前用户是否存在地址, 如果没有, 则新增为'默认地址'
        int isDefault = 0;
        List<UserAddress> addressList = this.queryAll(address.getUserId());
        if (addressList == null || addressList.isEmpty()) {
            isDefault = 1;
        }
        // 2.保存地址到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(address, userAddress);
        userAddress.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(userAddress.getCreatedTime());

        addressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);

        addressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateUserAddress(AddressBo addressBo) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo, userAddress);
        userAddress.setUpdatedTime(new Date());
        userAddress.setId(addressBo.getAddressId());

        addressMapper.updateByPrimaryKeySelective(userAddress);
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setId(addressId);

        return addressMapper.selectOne(address);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        //1. 查找默认地址
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list = addressMapper.select(userAddress);
        for (UserAddress ua : list) {
            if (ua.getIsDefault().equals(YesOrNo.YES.type)) {
                ua.setIsDefault(YesOrNo.NO.type);
                addressMapper.updateByPrimaryKeySelective(ua);
            }
            UserAddress defaultAddress = new UserAddress();
            defaultAddress.setId(addressId);
            defaultAddress.setUserId(userId);
            defaultAddress.setIsDefault(YesOrNo.YES.type);
            addressMapper.updateByPrimaryKeySelective(defaultAddress);
        }

    }
}
