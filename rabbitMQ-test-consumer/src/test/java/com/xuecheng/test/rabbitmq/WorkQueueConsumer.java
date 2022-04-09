package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class WorkQueueConsumer {
    //队列名称
    private static final String TEST_QUEUE = "test_work_queue";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        //设置虚拟机, 一个 mq 服务可以设置多个虚拟机, 每个虚拟机相当于一个独立的 mq
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        try {
            //建立连接
            connection = connectionFactory.newConnection();
            //创建 session channel
            Channel channel = connection.createChannel();
            //声明队列, Producer 和 Consumer 都要声明以防止没声明的先启动
            /**
             * queue 队列名称
             * durable 是否持久化
             * exclusive 队列只允许在该连接中访问,如果连接关闭队列自动删除, 如果将此参数设置为 true 可用于临时队列的创建
             * autoDelete 队列不再使用时是否自动删除, 如果将此参数和 exclusive 参数设置为 true 就可以实现临时队列
             * arguments 参数, 可以设置一个队列的扩展参数
             */
            channel.queueDeclare(TEST_QUEUE, true, false, false, null);

            //监听队列
            /**
             * queue 队列名称
             * autoAck 自动回复
             * callback 消费实体
             */
            DefaultConsumer callback = new DefaultConsumer(channel) {
                /**
                 *
                 * @param consumerTag
                 * @param envelope      通过 envelope 获得消息的属性
                 * @param properties
                 * @param body
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String exchange = envelope.getExchange();
                    long messageId = envelope.getDeliveryTag();
                    System.out.println("receive message: " + new String(body, "UTF-8"));
                }
            };
            channel.basicConsume(TEST_QUEUE, true, callback);

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
        }
    }
}
