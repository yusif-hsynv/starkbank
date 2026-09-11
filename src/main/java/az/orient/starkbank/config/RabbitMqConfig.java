package az.orient.starkbank.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${sample.rabbitmq.exchange}")
    private String exchange;

    @Value("${sample.rabbitmq.queue}")
    private String queueName;

    @Value("${sample.rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue firstStepQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Queue secondStepQueue() {
        return new Queue("secondStepQueue", false);
    }

    @Bean
    public Queue thirdStepQueue() {
        return new Queue("thirdStepQueue", false);
    }

    @Bean
    public Binding binding(Queue firstStepQueue, DirectExchange exchange) {
        return BindingBuilder.bind(firstStepQueue).to(exchange).with(routingKey);
    }

    @Bean
    public Binding secondBinding(Queue secondStepQueue, DirectExchange exchange) {
        return BindingBuilder.bind(secondStepQueue).to(exchange).with("secondRoute");
    }

    @Bean
    public Binding thirdBinding(Queue thirdStepQueue, DirectExchange exchange) {
        return BindingBuilder.bind(thirdStepQueue).to(exchange).with("thirdRoute");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}