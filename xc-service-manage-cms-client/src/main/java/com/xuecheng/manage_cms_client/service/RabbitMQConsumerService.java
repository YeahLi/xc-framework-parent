package com.xuecheng.manage_cms_client.service;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class RabbitMQConsumerService {
    @Autowired
    PageService pageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg) throws IOException {
        //解析消息
        Map map = JSON.parseObject(msg);
        String pageId = (String) map.get("pageId");

        pageService.downloadPage(pageId);
    }
}
