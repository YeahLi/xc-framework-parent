package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final String EXCHANGE_ROUTING_CMS_POSTPAGE = "exchange_routing_cms_postpage";

    //声明交换机
    @Bean(EXCHANGE_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_ROUTING_CMS_POSTPAGE() {
        return ExchangeBuilder.directExchange(EXCHANGE_ROUTING_CMS_POSTPAGE).durable(true).build();
    }
}
