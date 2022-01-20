package br.com.avf.microservices.stock.service;

import br.com.avf.microservices.stock.protocol.Order;

/**
 * @author angelo.vicente@veolia.com
 */
public interface ProductService {
    void updateQuantity(Order order);
}
