package br.com.avf.microservices.order.service;

import br.com.avf.microservices.order.domain.Order;
import br.com.avf.microservices.order.domain.OrderRepository;
import br.com.avf.microservices.order.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.avf.microservices.order.domain.Order.OrderStatus.*;

/**
 * @author angelo.vicente@veolia.com
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public Order save(Order order) {
        order.setStatus(NEW);
        publish(order);
        log.info(">>> Salvando a ordem '{}'", order);
        return repository.save(order);
    }

    private void publish(Order order) {
        log.info(">>> Publicando a ordem '{}'", order);
        publisher.publishEvent(OrderCreatedEvent.builder()
                .transactionId(UUID.randomUUID().toString().toUpperCase())
                .order(order)
                .build());
    }

    @Override
    public List<Order> all() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void updateOrderAsDone(Long orderId) {
        log.info(">>> Atualizando a ordem '{}' para '{}'", orderId, DONE);
        Optional<Order> optional = repository.findById(orderId);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setStatus(DONE);
            repository.save(order);
        } else {
            log.error("::: Nao pode atualizar a ordem para o status '{}', ordem '{}' nao localizada", DONE, orderId);
        }
    }

    @Transactional
    @Override
    public void cancel(Long orderId) {
        log.debug(">>> Cancelando a ordem '{}'", orderId);
        Optional<Order> optional = repository.findById(orderId);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setStatus(CANCELED);
            repository.save(order);
            log.info(">>> Order '{}' cancelada", order.getId());
        } else {
            log.error("Nao pode localizar a ordem pela transacao '{}'", orderId);
        }
    }

}
