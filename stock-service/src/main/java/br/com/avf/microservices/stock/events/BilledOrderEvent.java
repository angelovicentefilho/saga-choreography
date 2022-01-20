package br.com.avf.microservices.stock.events;

import br.com.avf.microservices.stock.protocol.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author angelo.vicente@veolia.com
 */
@Setter
@Getter
@NoArgsConstructor
public class BilledOrderEvent {
    private String transactionId;
    private Order order;
}
