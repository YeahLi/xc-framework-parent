package com.xuecheng.test.freemarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemakerTest {
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        String classpath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classpath+"/templates/"));

        Template template = configuration.getTemplate("test1.ftl");
        Map map = getMap();
        //éæå
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//        System.out.println(html);
        InputStream inputStream = IOUtils.toInputStream(html, "UTF-8");
        FileOutputStream outputStream = new FileOutputStream(classpath + "/templates/" + "test01.html");
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    @Test
    public void testGenerateHtmlFromString() throws IOException, TemplateException {
        String templateStr = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utfâ8\">\n" +
                "    <title>Hello World!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    Hello ${name}!" +
                "</body>\n" +
                "</html>";

        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateStr);

        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setTemplateLoader(stringTemplateLoader);

        Template template = configuration.getTemplate("template", "utf-8");
        Map map = getMap();
        //éæå
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        InputStream inputStream = IOUtils.toInputStream(html, "UTF-8");
        String classpath = this.getClass().getResource("/").getPath();
        FileOutputStream outputStream = new FileOutputStream(classpath + "/templates/" + "test02.html");
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    //è·åæ°æ®æ¨æ
    public Map getMap() {
        Map map = new HashMap();

        map.put("name","é»é©¬ç¨åºå");

        Student stu1 = new Student();
        stu1.setName("å°æ"); stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());

        Student stu2 = new Student(); stu2.setName("å°çº¢");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
        stu2.setBirthday(new Date());

        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);

        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        //åæ°æ®æ¨¡åæ¾æ°æ®
        map.put("stus",stus);
        //åå¤mapæ°æ®
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);

        //åæ°æ®æ¨¡åæ¾æ°æ®
        map.put("stu1",stu1);

        //åæ°æ®æ¨¡åæ¾æ°æ®
        map.put("stuMap",stuMap);

        return map;
    }
}
