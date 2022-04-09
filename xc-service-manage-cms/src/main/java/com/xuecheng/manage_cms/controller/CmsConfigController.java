package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/data-models")
public class CmsConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/{id}")
    public CmsConfig getDataModelById(@PathVariable String id) {
        return configService.getCmsConfigById(id);
    }
}
