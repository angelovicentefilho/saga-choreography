package br.com.avf.microservices.payment.events;

import br.com.avf.microservices.payment.protocol.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author angelo.vicente@veolia.com
 */
@Getter
@Setter
@NoArgsConstructor
public class RefundPaymentEvent {
    private String transactionId;
    private Order order;
}