package br.com.avf.microservices.order.handler;

import br.com.avf.microservices.order.events.OrderCreatedEvent;
import br.com.avf.microservices.order.utility.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final RabbitTemplate rabbit;
    private final Converter converter;

    @Value("${queue.order-create}")
    private String queueCreatedName;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        log.info(">>> Enviando a ordem criada para '{}', evento '{}'", queueCreatedName, event);
        rabbit.convertAndSend(queueCreatedName, converter.toJson(event));
    }

}
