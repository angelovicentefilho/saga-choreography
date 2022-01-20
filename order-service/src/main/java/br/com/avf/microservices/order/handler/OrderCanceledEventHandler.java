package br.com.avf.microservices.order.handler;

import br.com.avf.microservices.order.events.OrderCanceledEvent;
import br.com.avf.microservices.order.service.OrderService;
import br.com.avf.microservices.order.utility.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCanceledEventHandler {

    private final Converter converter;
    private final OrderService orderService;

    @RabbitListener(queues = {"${queue.order-canceled}"})
    public void handle(@Payload String payload) {
        log.info(">>> Recebendo uma ordem de cancelamento '{}'", payload);
        OrderCanceledEvent event = converter.toObject(payload, OrderCanceledEvent.class);
        orderService.cancel(event.getOrder().getId());
    }

}
