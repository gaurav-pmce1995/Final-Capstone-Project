package com.kanban.taskmanagement.config;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KanbanRabbitMQConfig {

    private static final String QUEUE_NAME = "kanban.queue";
    private static final String EXCHANGE_NAME = "kanban.exchange";
    private static final String ROUTING_KEY = "kanban.routingKey";

    @Bean
    public Queue registerQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
    *@Bean
    public Binding bindingUser(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue)
                .to(directExchange)
                .with(ROUTING_KEY);
    }*/

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
