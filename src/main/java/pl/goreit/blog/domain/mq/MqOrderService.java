package pl.goreit.blog.domain.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.CreateOrderRequest;
import pl.goreit.api.generated.OrderResponse;

import static pl.goreit.blog.configuration.config.RabbitConfig.CREATE_ORDER_Q_IN;
import static pl.goreit.blog.configuration.config.RabbitConfig.CREATE_ORDER_Q_OUT;

@Component
public class MqOrderService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public MqOrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public OrderResponse sendOrder(CreateOrderRequest createOrderRequest) throws JsonProcessingException {
        String jsonCreateOrderRequest = objectMapper.writeValueAsString(createOrderRequest);
        System.out.println("Sending -> " + jsonCreateOrderRequest);
        rabbitTemplate.convertAndSend(CREATE_ORDER_Q_IN, jsonCreateOrderRequest);

        Message receive = rabbitTemplate.receive(CREATE_ORDER_Q_OUT, 10000);
        byte[] body = receive.getBody();
        String stringJson = new String(body);

        System.out.println(stringJson);
        return objectMapper.readValue(stringJson, OrderResponse.class);
    }


}
