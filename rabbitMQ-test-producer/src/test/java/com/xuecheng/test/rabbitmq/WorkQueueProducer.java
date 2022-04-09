package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class WorkQueueProducer {

    //队列名称
    private static final String TEST_QUEUE = "test_queue";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        //设置虚拟机, 一个 mq 服务可以设置多个虚拟机, 每个虚拟机相当于一个独立的 mq
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            //建立连接
            connection = connectionFactory.newConnection();
            //创建 session channel
            channel = connection.createChannel();
            //声明队列
            /**
             * queue 队列名称
             * durable 是否持久化
             * exclusive 队列只允许在该连接中访问,如果连接关闭队列自动删除, 如果将此参数设置为 true 可用于临时队列的创建
             * autoDelete 队列不再使用时是否自动删除, 如果将此参数和 exclusive 参数设置为 true 就可以实现临时队列
             * arguments 参数, 可以设置一个队列的扩展参数
             */
            channel.queueDeclare(TEST_QUEUE, true, false, false, null);

            //发送消息
            /**
             * exchange 交换机, 如果不指定使用默认
             * routingKey 路由 key, 交换机根据路由 key 将消息转发到指定的队列, 默认交换机的 routing key 要设置为队列名称
             * props, 消息的属性
             * body, 消息内容
             */
            String message = "hello world 黑马程序员";
            channel.basicPublish("", TEST_QUEUE, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Send to mq: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }
    }
}
