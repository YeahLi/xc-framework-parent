package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    private TemplateService templateService;

    @Override
    @GetMapping("/cms/templates")
    public QueryResponseResult getTemplateList() {
        return new QueryResponseResult(
                CommonCode.SUCCESS,
                templateService.getTemplateList()
        );
    }
}
