package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.cms.CmsException;
import com.xuecheng.framework.exception.cms.CmsPageExistException;
import com.xuecheng.framework.exception.cms.FreemarkerException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.dao.GridFsRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private GridFsRepository gridFsRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    public QueryResult<CmsPage> findPageList(int page, int size, QueryPageRequest queryPageRequest) {
        validateFindPageListInput(page, size, queryPageRequest);

        Pageable pageable = PageRequest.of(page-1, size);

        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(queryPageRequest.getSiteId());
        cmsPage.setId(queryPageRequest.getPageId());
        cmsPage.setPageName(queryPageRequest.getPageName());
        cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        cmsPage.setPageAlias(queryPageRequest.getPageAlias());
        // 模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        QueryResult<CmsPage> result = new QueryResult<>();
        result.setList(all.getContent());
        result.setTotal(all.getTotalElements());
        return result;
    }

    private void validateFindPageListInput(int page, int size, QueryPageRequest queryPageRequest) {
        if (page < 1) {

        }

        if (size <= 0) {

        }

        if (queryPageRequest != null) {

        }

    }

    public CmsPageResult createNewPage(CmsPage cmsPage) {
        validateCreatePageRequest(cmsPage);

        cmsPage.setId(null);
        cmsPage = cmsPageRepository.save(cmsPage);

        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    private void validateCreatePageRequest(CmsPage cmsPage) {
        CmsPage result = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (result != null) {
            throw new CmsPageExistException(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        }
    }

    public CmsPageResult findPageById(String id) {
        Optional<CmsPage> result = cmsPageRepository.findById(id);
        return result.map(cmsPage -> new CmsPageResult(CommonCode.SUCCESS, cmsPage)).orElse(null);
    }

    public CmsPageResult updatePage(String id, CmsPage cmsPage) {
        if (findPageById(id) == null) {
            throw new CmsException("Invalid page id");
        }
        cmsPage.setId(id);
        cmsPage = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    public ResponseResult deletePageById(String id) {
        Optional<CmsPage> result = cmsPageRepository.findById(id);
        result.ifPresent(cmsPage -> cmsPageRepository.deleteById(cmsPage.getId()));
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public String generatePageHtml(String id) {
        Optional<CmsPage> page = cmsPageRepository.findById(id);
        if (!page.isPresent()) {
            return null;
        }

        Optional<CmsTemplate> template = cmsTemplateRepository.findById(page.get().getTemplateId());
        if (!template.isPresent()) {
            return null;
        }

        return getHtml(template.get(), getModelByDataUrl(page.get()));
    }

    private Map getModelByDataUrl(CmsPage page) {
        if(StringUtils.isBlank(page.getDataUrl())) {
            return null;
        }

        return restTemplate.getForObject(page.getDataUrl(), Map.class);
    }

    private String getTemplateContent(CmsTemplate cmsTemplate) throws IOException {
        return gridFsRepository.getFileContent(cmsTemplate.getTemplateFileId());
    }


    private String getHtml(CmsTemplate cmsTemplate, Map data) {
        try {
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            templateLoader.putTemplate(cmsTemplate.getTemplateName(), getTemplateContent(cmsTemplate));

            Configuration configuration = new Configuration(Configuration.getVersion());
            configuration.setTemplateLoader(templateLoader);

            Template template = configuration.getTemplate(cmsTemplate.getTemplateName(), "UTF-8");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        } catch (IOException exception) {
            throw new FreemarkerException(exception);
        } catch (TemplateException exception) {
            throw new FreemarkerException("Invalid template " + cmsTemplate.getTemplateName());
        }
    }

    public CmsPageResult postPage(String pageId) {
        CmsPage cmsPage = cmsPageRepository.findById(pageId).orElse(null);
        if (cmsPage == null) {
            throw new CmsException("Invalid pageId");
        }
        String html = generatePageHtml(pageId);
        if (StringUtils.isBlank(html)) {
            return new CmsPageResult(CmsCode.CMS_GENERATEHTML_HTMLISNULL, cmsPage);
        }

        String fileId = savePageHtml(cmsPage);
        cmsPage.setHtmlFileId(fileId);
        cmsPageRepository.save(cmsPage);

        sendNoticeMsg(pageId);

        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    private String savePageHtml(CmsPage cmsPage) {
        return gridFsRepository.saveCmsPage(cmsPage);
    }

    private void sendNoticeMsg(String pageId) {
        Map<String, String> map = new HashMap<>();
        map.put("pageId", pageId);
        String message = JSON.toJSONString(map);

        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_ROUTING_CMS_POSTPAGE, routingKey, message);
    }
}
