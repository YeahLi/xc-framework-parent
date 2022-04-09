package com.xuecheng.test.rabbitmq.consumer;

import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiveHandler {
    @RabbitListener(queues = {RabbitmqConfig.TOPIC_QUEUE_01})
    public void consumeQueue01(String msg) {
        System.out.println(RabbitmqConfig.TOPIC_QUEUE_01 + ":" + msg);
    }

    @RabbitListener(queues = {RabbitmqConfig.TOPIC_QUEUE_02})
    public void consumeQueue02(String msg) {
        System.out.println(RabbitmqConfig.TOPIC_QUEUE_02 + ":" + msg);
    }

}
