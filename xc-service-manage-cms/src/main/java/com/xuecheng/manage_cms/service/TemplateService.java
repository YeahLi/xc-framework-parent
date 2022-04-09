package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    public QueryResult<CmsTemplate> getTemplateList() {
        List<CmsTemplate> templates = cmsTemplateRepository.findAll();
        QueryResult<CmsTemplate> result = new QueryResult<>();
        result.setList(templates);
        return result;
    }
}
