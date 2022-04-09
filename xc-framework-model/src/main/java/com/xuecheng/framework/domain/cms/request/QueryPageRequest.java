package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接收页面查询的查询条件
 */
@Data
public class QueryPageRequest {
    @ApiModelProperty("站点id")
    private String siteId;
    @ApiModelProperty("页面ID")
    private String pageId;
    @ApiModelProperty("页面名称")
    private String pageName;
    @ApiModelProperty("页面别名")
    private String pageAlias;
    @ApiModelProperty("模版id")
    private String templateId;
}
