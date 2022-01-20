package br.com.avf.microservices.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author angelo.vicente@veolia.com
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
