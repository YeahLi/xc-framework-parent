package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    public CmsConfig getCmsConfigById(String id) {
        return cmsConfigRepository.findById(id).orElseGet(null);
    }
}
