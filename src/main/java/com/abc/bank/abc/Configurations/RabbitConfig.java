package com.abc.bank.abc.Configurations;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig
{
    public static final String CREATED_TOKEN_QUEUE = "created-token-queue";
    public static final String CREATED_TOKEN_EXCHANGE = "created-token-exchange";

    @Bean
    Queue createdTokenQueue() {
        return QueueBuilder.durable(CREATED_TOKEN_QUEUE).build();
    }

    @Bean
    Exchange createdTokenExchange() {
        return ExchangeBuilder.topicExchange(CREATED_TOKEN_EXCHANGE).build();
    }

    @Bean
    Binding binding(Queue createdTokenQueue, TopicExchange createdTokenExchange) {
        return BindingBuilder.bind(createdTokenQueue).to(createdTokenExchange).with(CREATED_TOKEN_QUEUE);
    }
}
