package com.xuecheng.framework.exception.cms;

public class CmsPageExistException extends CmsException {
    public CmsPageExistException(String pageName, String siteId, String webPath) {
        super(String.format("Page with pageName=%s, siteId=%s, webPath=%s is existing.", pageName, siteId, webPath));
    }
}
