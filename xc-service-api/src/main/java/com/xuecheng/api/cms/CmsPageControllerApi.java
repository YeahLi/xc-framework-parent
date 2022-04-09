package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

@Api("cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page", value = "页码", required=true, paramType="query", dataType="int"),
            @ApiImplicitParam(name="size", value = "每页记录数", required=true, paramType="query", dataType="int")
    })
    QueryResponseResult findPageList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("新增页面")
    CmsPageResult createPage(CmsPage cmsPage);

    @ApiOperation("修改页面")
    CmsPageResult updatePage(String id, CmsPage cmsPage);

    @ApiOperation("根据ID查找页面")
    CmsPageResult findById(@PathVariable String id);

    @ApiOperation("根据ID删除")
    ResponseResult deleteById(@PathVariable String id);

    @ApiOperation("根据 id 发布页面")
    CmsPageResult postPage(String id);
}
