package com.xybh.controller;

import com.xybh.pojo.UserAddress;
import com.xybh.pojo.bo.AddressBo;
import com.xybh.service.AddressService;
import com.xybh.utils.JSONResult;
import com.xybh.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 19:25 2021/1/23
 * @Modified:
 */
@Api(value = "用户地址接口", tags = {"用户地址相关接口"})
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货列表", notes = "根据用户id查询收货列表", httpMethod = "POST")
    @RequestMapping("/list")
    public JSONResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        List<UserAddress> list = addressService.queryAll(userId);
        return JSONResult.ok(list);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @RequestMapping("/add")
    public JSONResult add(@RequestBody AddressBo addressBo) {
        if (addressBo == null) {
            return JSONResult.errorMsg("");
        }
        JSONResult result = checkAddress(addressBo);
        if (result.getStatus() != 200) {
            return result;
        }
        addressService.addNewUserAddress(addressBo);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @RequestMapping("/update")
    public JSONResult update(@RequestBody AddressBo addressBo) {
        if (StringUtils.isBlank(addressBo.getUserId())) {
            return JSONResult.errorMsg("修改地址错误: 用户不能为空");
        }
        JSONResult result = checkAddress(addressBo);
        if (result.getStatus() != 200) {
            return result;
        }
        addressService.updateUserAddress(addressBo);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @RequestMapping("/delete")
    public JSONResult delete(@RequestParam String userId,
                             @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }
        addressService.deleteUserAddress(userId, addressId);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户修改默认地址", notes = "用户修改默认地址", httpMethod = "POST")
    @RequestMapping("/setDefault")
    public JSONResult setDefault(@RequestParam String userId,
                             @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }
        addressService.updateUserAddressToBeDefault(userId, addressId);
        return JSONResult.ok();
    }

    private JSONResult checkAddress(AddressBo addressBo) {
        String receiver = addressBo.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人名字不能太长");
        }
        String mobile = addressBo.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("联系方式不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("收货人手机号长度不为11位");
        }
        if (!MobileEmailUtils.checkMobileIsOk(mobile)) {
            return JSONResult.errorMsg("收货人手机号格式不正确");
        }
        String province = addressBo.getProvince();
        String city = addressBo.getCity();
        String detail = addressBo.getDetail();
        String district = addressBo.getDistrict();
        if (StringUtils.isBlank(province) || StringUtils.isBlank(city) || StringUtils.isBlank(detail) || StringUtils.isBlank(district)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }
        return JSONResult.ok();
    }
}
