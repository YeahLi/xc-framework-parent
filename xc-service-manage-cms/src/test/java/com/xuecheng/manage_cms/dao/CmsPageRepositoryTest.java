package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    private static final String PAGE_NAME = "testPage";
    private static final String DATA_URL = "/test";

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll() {
        List<CmsPage> result = cmsPageRepository.findAll();
        System.out.println(result);
    }

    @Test
    public void testFindPage() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> result = cmsPageRepository.findAll(pageable);
        System.out.println(result.getContent());
    }

    @Test
    public void testSave() {
        CmsPage page = createCmsPage();
        cmsPageRepository.save(page);

        assertThat(!StringUtils.isBlank(page.getId()));
        Optional<CmsPage> result = cmsPageRepository.findById(page.getId());
        assertThat(result.isPresent());

        cmsPageRepository.deleteById(page.getId());
    }

    @Test
    public void testUpdate() {
        CmsPage page = createCmsPage();
        cmsPageRepository.save(page);

        Optional<CmsPage> result = cmsPageRepository.findById(page.getId());
        assertThat(result.isPresent());

        CmsPage savedPage = result.get();
        assertThat(savedPage.getPageName().equals(PAGE_NAME));
        assertThat(savedPage.getDataUrl().equals(DATA_URL));

        savedPage.setDataUrl("/updateTest");
        cmsPageRepository.save(savedPage);
        result = cmsPageRepository.findById(page.getId());
        CmsPage updatedPage = result.get();
        assertThat(updatedPage.getDataUrl().equals("/updateTest"));

        cmsPageRepository.deleteById(page.getId());
    }

    private CmsPage createCmsPage() {
        CmsPage page = new CmsPage();
        page.setPageName(PAGE_NAME);
        page.setDataUrl(DATA_URL);
        return page;
    }
}
