package com.xybh.exception;

import com.xybh.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 0:28 2021/1/28
 * @Modified:
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 上传文件超过500K, 捕获MaxUploadSizeExceededException
     * @return JSONResult
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException exception){
        return JSONResult.errorMsg("文件大小不能操作500k,请压缩图片或降低图片质量上传");
    }
}
