package br.com.avf.microservices.payment.events;

import br.com.avf.microservices.payment.protocol.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author angelo.vicente@veolia.com
 */
@Setter
@Getter
@NoArgsConstructor
public class OrderCreatedEvent {
    private String transactionId;
    private Order order;
}