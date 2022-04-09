package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("cms站点管理接口，提供站点的增、删、改、查")
public interface CmsSiteControllerApi {
    @ApiOperation("返回所有站点")
    QueryResponseResult getSiteList();
}
