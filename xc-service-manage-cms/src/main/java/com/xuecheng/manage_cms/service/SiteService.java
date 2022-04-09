package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    public QueryResult<CmsSite> getSiteList() {
        List<CmsSite> siteList = cmsSiteRepository.findAll();
        QueryResult<CmsSite> result = new QueryResult<>();
        result.setList(siteList);
        return result;
    }
}
