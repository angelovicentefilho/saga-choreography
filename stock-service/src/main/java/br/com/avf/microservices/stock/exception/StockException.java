package br.com.avf.microservices.stock.exception;

/**
 * @author angelo.vicente@veolia.com
 */
public class StockException extends RuntimeException {
    public StockException(String message) {
        super(message);
    }
}