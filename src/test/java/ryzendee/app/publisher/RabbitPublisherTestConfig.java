package ryzendee.app.publisher;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@EnableRabbit
class RabbitPublisherTestConfig {

    public static final String TEST_QUEUE = "test_deals_contractor_queue";

    @Value("${rabbit.contractor.exchange}")
    private String contractorExchangeName;

    @Value("${rabbit.contractor.routing-key}")
    private String contractorRoutingKey;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Bean
    public DirectExchange contractorExchange() {
        return new DirectExchange(contractorExchangeName, true, false);
    }

    @Bean
    public Queue testQueue() {
        return QueueBuilder.durable(TEST_QUEUE).build();
    }

    @Bean
    public Binding testBinding(Queue testQueue, DirectExchange testExchange) {
        return BindingBuilder.bind(testQueue).to(testExchange).with(contractorRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
