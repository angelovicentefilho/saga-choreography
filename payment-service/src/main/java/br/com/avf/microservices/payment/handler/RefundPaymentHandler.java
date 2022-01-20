package br.com.avf.microservices.payment.handler;

import br.com.avf.microservices.payment.events.RefundPaymentEvent;
import br.com.avf.microservices.payment.service.PaymentService;
import br.com.avf.microservices.payment.utility.Converter;
import br.com.avf.microservices.payment.utility.TransactionIdHolder;
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
public class RefundPaymentHandler {

    private final Converter converter;
    private final PaymentService service;
    private final TransactionIdHolder transactionIdHolder;

    @RabbitListener(queues = {"${queue.order-create}"})
    public void handle(@Payload String payload) {
        log.debug(">>> Recenbendo uma requisiçao de estorno '{}' ", payload);
        RefundPaymentEvent event = converter.toObject(payload, RefundPaymentEvent.class);
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        service.refund(event.getOrder().getId());
    }
}
