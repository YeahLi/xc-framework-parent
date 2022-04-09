package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.dao.GridFsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {
    @Autowired
    PageService pageService;

    @Autowired
    GridFsRepository gridFsRepository;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testGetPageHtml() throws FileNotFoundException {
        CmsPage cmsPage = createPage();
        try {
            String html = pageService.generatePageHtml(cmsPage.getId());
            System.out.println(html);
        } finally {
            cleanUp(cmsPage);
        }
    }

    @Test
    public void createPageTest() throws FileNotFoundException {
        createPage();
    }

    private CmsPage createPage() throws FileNotFoundException {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageWebPath(new Date().toString());
        cmsPage.setPageName("index_banner.html");
        cmsPage.setPageAlias("轮播图");
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageCreateTime(new Date());
        cmsPage.setTemplateId(createTemplate().getId());
        cmsPage.setPageType("0");
        cmsPage.setDataUrl("http://localhost:31001/cms/data-models/5a791725dd573c3574ee333f");
        return cmsPageRepository.save(cmsPage);
    }

    private CmsTemplate createTemplate() throws FileNotFoundException {
        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplate.setTemplateName("轮播图模板");
        cmsTemplate.setSiteId("5a751fab6abb5044e0d19ea1");
        String fileId = saveTemplate();
        cmsTemplate.setTemplateFileId(fileId);
        return cmsTemplateRepository.save(cmsTemplate);
    }

    private String saveTemplate() throws FileNotFoundException {
        String classPath = this.getClass().getResource("/").getPath();
        return gridFsRepository.saveFileContent(classPath + "/templates/index_banner.ftl", "轮播图测试文件");
    }

    private void cleanUp(CmsPage cmsPage) {
        //delete file
        String fileId = cmsTemplateRepository.findById(cmsPage.getTemplateId()).get().getTemplateFileId();
        gridFsRepository.deleteFile(fileId);

        //delete template
        cmsTemplateRepository.deleteById(cmsPage.getTemplateId());

        //delete page
        cmsPageRepository.deleteById(cmsPage.getId());
    }
}
