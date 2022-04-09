package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class PubSubConsumer02 {
    //队列名称
    private static final String PUBSUB_QUEUE_01 = "pubsub_queue_01";
    private static final String PUBSUB_QUEUE_02 = "pubsub_queue_02";
    private static final String EXCHANGE = "pubsub_exchange";

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
            channel.queueDeclare(PUBSUB_QUEUE_01, true, false, false, null);
            channel.queueDeclare(PUBSUB_QUEUE_02, true, false, false, null);

            //声明交换机
            /**
             * 交换机类型:
             *  fanout: pub/sub 模式
             *  direct: routing 模式
             *  topic:  topics 模式
             *  headers: headers 模式
             */
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);

            //绑定队列到交换机
            /**
             * queue 队列名
             * exchange 交换机名
             * routingKey 路由 key, 交换机根据路由 key 将消息转发到指定的队列, 默认pub/sub交换机的 routing key 为空
             */
            channel.queueBind(PUBSUB_QUEUE_01, EXCHANGE, "");
            //channel.queueBind(PUBSUB_QUEUE_02, EXCHANGE, "");

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
            channel.basicConsume(PUBSUB_QUEUE_01, true, callback);

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
        }
    }
}
