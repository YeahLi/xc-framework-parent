package com.xuecheng.framework.exception.cms;

public class FreemarkerException extends CmsException {
    public FreemarkerException(Exception exception) {
        super(exception.getStackTrace().toString());
    }

    public FreemarkerException(String message) {
        super(message);
    }
}
