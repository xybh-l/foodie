package com.xybh.controller.center;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.xybh.controller.BaseController;
import com.xybh.pojo.Users;
import com.xybh.pojo.bo.center.CenterUserBO;
import com.xybh.pojo.vo.UsersVO;
import com.xybh.resource.FileUpload;
import com.xybh.service.center.CenterUserService;
import com.xybh.utils.CookieUtils;
import com.xybh.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 17:40 2021/1/27
 * @Modified:
 */
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "PUT")
    @PutMapping("uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {

        // 定义头像保存的地址
//        String fileSpace = IMAGE_USER_FACE_URL;
        String fileSpace = fileUpload.getImageUserFaceLocation();
        // 在路径上为每个用户增加一个userId, 用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;

        // 开始文件上传
        if (file != null) {
            // 获取文件上传的文件名称
            String filename = file.getOriginalFilename();
            if (StringUtils.isNotBlank(filename)) {
                // 文件重命名  imooc-face.png -> ["imooc-face", "png"]
                String[] fileNameArray = filename.split("\\.");

                // 获取文件的后缀名
                String suffix = fileNameArray[fileNameArray.length - 1];

                if(!"png".equalsIgnoreCase(suffix) && !"jpg".equalsIgnoreCase(suffix) && !"jpeg".equalsIgnoreCase(suffix)){
                    return JSONResult.errorMsg("图片格式不正确");
                }

                // face-{userid}.png
                // 文件名重组  覆盖式上传, 增量式: 额外拼接当前时间
                String newFileName = "face-" + userId + "-" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + "." + suffix;

                // 文件上传的最终保存位置
                String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                // 用于提供给web服务访问的地址
                uploadPathPrefix += ("/" + newFileName);
                FileOutputStream fileOutputStream = null;
                InputStream inputStream = null;
                try {
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        // 创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return JSONResult.errorMsg("文件不能为空!");
        }

        // 获取图片服务地址
        String imageServerUrl = fileUpload.getImageServerUrl();

        // 由于游览器可能存在缓存的情况,图片可以加上时间戳来及时更新图片
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix;
        // 更新用户头像到数据库
        Users user = centerUserService.updateUserFace(userId, finalUserFaceUrl);
        UsersVO usersVO = convertUsersVO(user);
        // 增加令牌token,整合进redis,分布式会话
        CookieUtils.setCookie(request, response, "user", JSON.toJSONString(usersVO), true);
        return JSONResult.ok();
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "PUT")
    @PutMapping("update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO userBo,
            BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return JSONResult.errorMap(errorMap);
        }
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        Users userResult = centerUserService.updateUserInfo(userId, userBo);
        // 增加令牌token,整合进redis,分布式会话
        UsersVO usersVO = convertUsersVO(userResult);
        CookieUtils.setCookie(request, response, "user", JSON.toJSONString(usersVO), true);

        return JSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>(8);
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error :
                errors) {
            // 发生验证错误所对应的某个属性
            String errorField = error.getField();
            // 发生验证错误的信息
            String message = error.getDefaultMessage();

            map.put(errorField, message);
        }
        return map;
    }

    private void setNullProperty(Users user) {
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
    }
}
