package br.com.avf.microservices.order.events;

import br.com.avf.microservices.order.domain.Order;
import lombok.*;

/**
 * @author angelo.vicente@veolia.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class OrderDoneEvent {
    private String transactionId;
    private Order order;
}
