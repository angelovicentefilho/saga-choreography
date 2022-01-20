package br.com.avf.microservices.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author angelo.vicente@veolia.com
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal value;
    private OrderStatus status;

    public enum OrderStatus {
        NEW, DONE, CANCELED
    }
}
