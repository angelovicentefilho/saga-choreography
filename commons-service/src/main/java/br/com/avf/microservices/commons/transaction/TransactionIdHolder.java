package br.com.avf.microservices.commons.transaction;

import org.springframework.stereotype.Component;

/**
 * @author angelo.vicente@veolia.com
 */
@Component
public class TransactionIdHolder {

    private final ThreadLocal<String> concurrentTransactionId = new ThreadLocal<>();

    public String getCurrentTransactionId() {
        return concurrentTransactionId.get();
    }

    public void setCurrentTransactionId(String transactionId) {
        concurrentTransactionId.set(transactionId);
    }
}
