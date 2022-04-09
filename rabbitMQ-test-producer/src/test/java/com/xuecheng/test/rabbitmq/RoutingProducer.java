package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RoutingProducer {

    //队列名称
    private static final String ROUTING_QUEUE_01 = "routing_queue_01";
    private static final String ROUTING_QUEUE_02 = "routing_queue_02";
    private static final String EXCHANGE = "routing_exchange";

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
            channel.queueDeclare(ROUTING_QUEUE_01, true, false, false, null);
            channel.queueDeclare(ROUTING_QUEUE_02, true, false, false, null);

            //声明交换机
            /**
             * 交换机类型:
             *  fanout: pub/sub 模式
             *  direct: routing 模式
             *  topic:  topics 模式
             *  headers: headers 模式
             */
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);

            //绑定队列到交换机
            /**
             * queue 队列名
             * exchange 交换机名
             * routingKey 路由 key, 交换机根据路由 key 将消息转发到指定的队列, 默认pub/sub交换机的 routing key 为空
             */
            channel.queueBind(ROUTING_QUEUE_01, EXCHANGE, "error");
            channel.queueBind(ROUTING_QUEUE_02, EXCHANGE, "error");
            channel.queueBind(ROUTING_QUEUE_02, EXCHANGE, "info");

            //发送消息
            /**
             * exchange 交换机, 如果不指定使用默认
             * routingKey 路由 key, 交换机根据路由 key 将消息转发到指定的队列, 默认交换机的 routing key 要设置为队列名称
             * props, 消息的属性
             * body, 消息内容
             */
            for (int i = 0; i < 2; i++) {
                String message = "Error";
                channel.basicPublish(EXCHANGE, "error", null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("Send to mq: " + message);
            }

            for (int i = 0; i < 3; i++) {
                String message = "Info";
                channel.basicPublish(EXCHANGE, "info", null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("Send to mq: " + message);
            }

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
