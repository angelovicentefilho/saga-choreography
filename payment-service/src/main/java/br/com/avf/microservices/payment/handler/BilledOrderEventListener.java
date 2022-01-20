package br.com.avf.microservices.payment.handler;

import br.com.avf.microservices.payment.events.BilledOrderEvent;
import br.com.avf.microservices.payment.utility.Converter;
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
public class BilledOrderEventListener {

    private final RabbitTemplate rabbit;
    private final Converter converter;

    @Value("${queue.billed-order}")
    private String queueBilledOrderName;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(BilledOrderEvent event) {
        log.info("Enviando a conta para '{}' do evento '{}'", queueBilledOrderName, event);
        rabbit.convertAndSend(queueBilledOrderName, converter.toJson(event));
    }
}
