package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String webPath);
}
