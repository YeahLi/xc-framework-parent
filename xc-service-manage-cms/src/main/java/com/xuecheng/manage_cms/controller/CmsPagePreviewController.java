package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class CmsPagePreviewController extends BaseController {
    @Autowired
    PageService pageService;

    @GetMapping("/cms/pages/{id}/preview")
    public void preview(@PathVariable String id) throws IOException {
        String html = pageService.generatePageHtml(id);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(html.getBytes(StandardCharsets.UTF_8));
    }
}
