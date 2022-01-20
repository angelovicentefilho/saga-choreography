package br.com.avf.microservices.stock.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author angelo.vicente@veolia.com
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    public Product(Long quantity) {
        this.quantity = quantity;
    }
}