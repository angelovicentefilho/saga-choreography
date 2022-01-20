package br.com.avf.microservices.order.handler;

import br.com.avf.microservices.order.events.OrderDoneEvent;
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
public class OrderDoneEventHandler {

    private final Converter converter;
    private final OrderService orderService;

    @RabbitListener(queues = {"${queue.order-done}"})
    public void handle(@Payload String payload) {
        log.info(">>> Recebendo uma ordem de DONE do evento '{}'", payload);
        OrderDoneEvent event = converter.toObject(payload, OrderDoneEvent.class);
        orderService.updateOrderAsDone(event.getOrder().getId());
    }

}
