package br.com.avf.microservices.stock.handler;


import br.com.avf.microservices.stock.events.BilledOrderEvent;
import br.com.avf.microservices.stock.exception.StockException;
import br.com.avf.microservices.stock.service.ProductService;
import br.com.avf.microservices.stock.utility.Converter;
import br.com.avf.microservices.stock.utility.TransactionIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@Component
public class BilledOrderHandler {
    @Resource
    private Converter converter;

    @Autowired
    private ProductService productService;
    @Resource
    private TransactionIdHolder transactionIdHolder;

    @RabbitListener(queues = {"${queue.billed-order}"})
    public void handle(@Payload String payload) {
        log.debug("Enviando a ordem de conta para  '{}'", payload);
        BilledOrderEvent event = converter.toObject(payload, BilledOrderEvent.class);
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        try {
            productService.updateQuantity(event.getOrder());
        } catch (StockException e) {
            log.error("Nao pode atualizar o estoque, rasao: {}", e.getMessage());
        }
    }
}
