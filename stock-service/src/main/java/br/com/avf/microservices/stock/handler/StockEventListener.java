package br.com.avf.microservices.stock.handler;

import br.com.avf.microservices.stock.events.OrderCanceledEvent;
import br.com.avf.microservices.stock.events.OrderDoneEvent;
import br.com.avf.microservices.stock.utility.Converter;
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
public class StockEventListener {

    private static final String ROUTING_KEY = "";

    private final RabbitTemplate rabbit;
    private final Converter converter;
    @Value("${queue.order-done}")
    private String queueOrderDoneName;
    @Value("${topic.order-canceled}")
    private String topicOrderCanceledName;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderDoneEvent(OrderDoneEvent event) {
        log.debug("Enviando a orden done '{}', evento: '{}'", queueOrderDoneName, event);
        rabbit.convertAndSend(queueOrderDoneName, converter.toJson(event));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onOrderCancelledEvent(OrderCanceledEvent event) {
        log.debug("Enviando a ordem de cancelamento para '{}', evento: '{}'", topicOrderCanceledName, event);
        rabbit.convertAndSend(topicOrderCanceledName, converter.toJson(event));
    }
}
