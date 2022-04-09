package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.exception.cms.CmsException;
import com.xuecheng.framework.exception.cms.CmsPageExistException;
import com.xuecheng.framework.exception.cms.FreemarkerException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice //控制器增强
public class ExceptionCatch {
    public static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS_MAP;
    public static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception exception) {
        if (EXCEPTIONS_MAP == null) {
            EXCEPTIONS_MAP = builder.build();
        }
        return new ResponseResult(EXCEPTIONS_MAP.getOrDefault(exception.getClass(), CommonCode.SERVER_ERROR));
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseResult customException(CustomException exception){
        return new ResponseResult(exception.getResultCode());
    }

    @ExceptionHandler(CmsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseResult cmsException(CmsException cmsException) {
        return new ResponseResult(cmsException.getResultCode());
    }

    @ExceptionHandler(CmsPageExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseResult cmsPageExistException(CmsPageExistException cmsPageExistException) {
        return new ResponseResult(cmsPageExistException.getResultCode());
    }

    @ExceptionHandler(FreemarkerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseResult freemarkerException(FreemarkerException freemarkerException) {
        return new ResponseResult(freemarkerException.getResultCode());
    }
}
