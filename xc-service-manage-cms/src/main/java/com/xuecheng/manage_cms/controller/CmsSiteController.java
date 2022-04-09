package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CmsSiteController implements CmsSiteControllerApi {
    @Autowired
    private SiteService siteService;

    @Override
    @GetMapping("/cms/sites")
    public QueryResponseResult getSiteList() {
        QueryResult<CmsSite> result = siteService.getSiteList();
        return new QueryResponseResult(CommonCode.SUCCESS, result);
    }
}
