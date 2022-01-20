package br.com.avf.microservices.order.controller;

import br.com.avf.microservices.order.domain.Order;
import br.com.avf.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class GetAllOrdersController {
    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok().body(service.all());
    }

}
