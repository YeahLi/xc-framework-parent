package com.xuecheng.test.rabbitmq;

import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitTemplateTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbitTemplate() {
        String errorMsg = "this is error aaa.bbb";
        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, "error.aaa.bbb", errorMsg);
        errorMsg = "this is error aaa";
        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, "error.aaa", errorMsg);
        String infoMsg = "this is info aaa";
        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, "info.aaa", infoMsg);
    }
}
