package com.xuecheng.test.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String TOPIC_QUEUE_01 = "topic_queue_01";
    public static final String TOPIC_QUEUE_02 = "topic_queue_02";
    public static final String TOPIC_EXCHANGE = "topic_exchange";

    //声明交换机
    @Bean(TOPIC_EXCHANGE)
    public Exchange getTopicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).durable(true).build();
    }

    //声明队列
    @Bean(TOPIC_QUEUE_01)
    public Queue getTopicQueue01() {
        return new Queue(TOPIC_QUEUE_01);
    }

    @Bean(TOPIC_QUEUE_02)
    public Queue getTopicQueue02() {
        return new Queue(TOPIC_QUEUE_02);
    }

    //绑定队列
    @Bean
    public Binding  bindTopicQueue01(@Qualifier(TOPIC_QUEUE_01) Queue queue, @Qualifier(TOPIC_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("error.*").noargs();
    }

    @Bean
    public Binding  bindTopicQueue02WithError(@Qualifier(TOPIC_QUEUE_02) Queue queue, @Qualifier(TOPIC_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("error.#").noargs();
    }

    @Bean
    public Binding  bindTopicQueue02WithInfo(@Qualifier(TOPIC_QUEUE_02) Queue queue, @Qualifier(TOPIC_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("info.#").noargs();
    }

}
