package br.com.avf.microservices.stock.protocol;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author angelo.vicente@veolia.com
 */

@Data
public class Order {

    private Long id;

    private String transactionId;

    private Long productId;

    private BigDecimal value;

    private Long quantity;
}