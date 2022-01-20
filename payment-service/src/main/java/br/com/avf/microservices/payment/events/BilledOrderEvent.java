package br.com.avf.microservices.payment.events;

import br.com.avf.microservices.payment.protocol.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author angelo.vicente@veolia.com
 */
@Data
@Builder
@AllArgsConstructor
public class BilledOrderEvent {
    private String transactionId;
    private final Order order;
}