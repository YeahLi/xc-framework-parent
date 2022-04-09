package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private PageService pageService;

    @Override
    @GetMapping("/cms/pages")
    public QueryResponseResult findPageList(
            @RequestParam int page,
            @RequestParam int size,
            QueryPageRequest request) {
        QueryResult<CmsPage> result = pageService.findPageList(page, size, request);
        return new QueryResponseResult(CommonCode.SUCCESS, result);
    }

    @Override
    @GetMapping("/cms/pages/{id}")
    public CmsPageResult findById(@PathVariable String id) {
        return pageService.findPageById(id);
    }

    @Override
    @PostMapping("/cms/pages")
    @ResponseStatus(HttpStatus.CREATED)
    public CmsPageResult createPage(@RequestBody CmsPage cmsPage) {
        return pageService.createNewPage(cmsPage);
    }

    @Override
    @PutMapping("/cms/pages/{id}")
    public CmsPageResult updatePage(@PathVariable String id, @RequestBody CmsPage cmsPage) {
        return pageService.updatePage(id, cmsPage);
    }

    @Override
    @DeleteMapping("/cms/pages/{id}")
    public ResponseResult deleteById(@PathVariable String id) {
        return pageService.deletePageById(id);
    }

    @PostMapping("/cms/pages/{id}/postPage")
    public CmsPageResult postPage(@PathVariable String id) {
        return pageService.postPage(id);
    }
}
