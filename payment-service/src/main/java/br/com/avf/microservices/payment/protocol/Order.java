package br.com.avf.microservices.payment.protocol;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author angelo.vicente@veolia.com
 */
@Data
public class Order {
    private Long id;
    private Long productId;
    private BigDecimal value;
    private Integer quantity;
}
