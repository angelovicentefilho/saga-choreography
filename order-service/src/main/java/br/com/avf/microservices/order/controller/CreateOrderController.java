package br.com.avf.microservices.order.controller;

import br.com.avf.microservices.order.domain.Order;
import br.com.avf.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class CreateOrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        log.info(">>> Criando uma nova ordem '{}'", order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }
}
