package com.xuecheng.framework.exception.cms;

import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.ResultCode;

public class CmsException extends CustomException {
    public CmsException(String message) {
        super(new ResultCode() {
            @Override
            public boolean success() {
                return false;
            }

            @Override
            public int code() {
                return 24000;
            }

            @Override
            public String message() {
                return message;
            }
        });
    }
}
