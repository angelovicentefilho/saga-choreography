package br.com.avf.microservices.payment.service;

import br.com.avf.microservices.payment.domain.Payment;
import br.com.avf.microservices.payment.protocol.Order;

/**
 * @author angelo.vicente@veolia.com
 */
public interface PaymentService {
    void charge(Order order);
    void refund(Long orderId);
}
