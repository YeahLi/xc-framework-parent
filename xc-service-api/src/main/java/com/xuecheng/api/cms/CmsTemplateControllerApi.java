package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("cms模板管理接口，提供模板的增、删、改、查")
public interface CmsTemplateControllerApi {
    @ApiOperation("返回所有模板")
    QueryResponseResult getTemplateList();
}
