package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    public static final String EXCHANGE_ROUTING_CMS_POSTPAGE = "exchange_routing_cms_postpage";

    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;

    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    //声明交换机
    @Bean(EXCHANGE_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_ROUTING_CMS_POSTPAGE() {
        return ExchangeBuilder.directExchange(EXCHANGE_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE() {
        return new Queue(queue_cms_postpage_name);
    }

    //绑定队列
    @Bean
    public Binding bindQueue(@Qualifier(QUEUE_CMS_POSTPAGE) Queue queue, @Qualifier(EXCHANGE_ROUTING_CMS_POSTPAGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
