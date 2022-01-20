package br.com.avf.microservices.order.service;

import br.com.avf.microservices.order.domain.Order;

import java.util.List;

/**
 * @author angelo.vicente@veolia.com
 */
public interface OrderService {
    Order save(Order order);
    void updateOrderAsDone(Long orderId);
    void cancel(Long orderId);
    List<Order> all();
}
