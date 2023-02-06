package com.tfa.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LivraisonConfiguration {

	@Bean
	public Exchange exchange() {
		return new TopicExchange("retour-client-exchange", true, false);
	}
	
	@Bean
	public Queue queue() {
		return new Queue("queue-retour",true,false,false);
	}

	@Bean
	public Binding binding() {
		return BindingBuilder
				.bind(queue())
				.to(exchange())
				.with("routing-key-retour")
				.noargs();
	}
	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory factory ) {
		RabbitTemplate template = new RabbitTemplate(factory);
		template.setMessageConverter(converter());
		return template;
	}
}
