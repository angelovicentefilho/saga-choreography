package br.com.avf.microservices.stock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author angelo.vicente@veolia.com
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
