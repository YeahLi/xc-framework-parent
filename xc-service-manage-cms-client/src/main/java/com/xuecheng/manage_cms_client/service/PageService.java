package com.xuecheng.manage_cms_client.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.GridFsRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    GridFsRepository gridFsRepository;

    @Value("${xuecheng.fs.portalDir}")
    public String portalDir;

    public void downloadPage(String pageId) throws IOException {
        CmsPage cmsPage = cmsPageRepository.findById(pageId).orElse(null);

        if (cmsPage == null) {
            return;
        }

        String htmlFileId = cmsPage.getHtmlFileId();

        //从 gridFS 中下载 html 文件
        String html = gridFsRepository.getFileContent(htmlFileId);

        //将 html 文件保存在服务器物理路径上
        String path = portalDir + cmsPage.getPagePhysicalPath();
        File dir = new File(path);
        dir.mkdirs();
        InputStream inputStream = IOUtils.toInputStream(html, "UTF-8");
        FileOutputStream outputStream = new FileOutputStream(path + cmsPage.getPageName());
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }
}
