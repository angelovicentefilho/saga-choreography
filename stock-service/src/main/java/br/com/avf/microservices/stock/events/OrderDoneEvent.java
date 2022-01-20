package br.com.avf.microservices.stock.events;

import br.com.avf.microservices.stock.protocol.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author angelo.vicente@veolia.com
 */
@Data
@AllArgsConstructor
public class OrderDoneEvent {
    private String transactionId;
    private final Order order;
}