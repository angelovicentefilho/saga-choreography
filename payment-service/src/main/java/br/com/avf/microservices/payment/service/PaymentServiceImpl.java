package br.com.avf.microservices.payment.service;

import br.com.avf.microservices.payment.domain.Payment;
import br.com.avf.microservices.payment.domain.PaymentRepository;
import br.com.avf.microservices.payment.events.BilledOrderEvent;
import br.com.avf.microservices.payment.protocol.Order;
import br.com.avf.microservices.payment.utility.TransactionIdHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static br.com.avf.microservices.payment.domain.Payment.PaymentStatus.BILLED;
import static br.com.avf.microservices.payment.domain.Payment.PaymentStatus.REFUND;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final ApplicationEventPublisher publisher;
    private final TransactionIdHolder transactionIdHolder;

    @Transactional
    @Override
    public void charge(Order order) {
        log.info(">>> Executando a ordem '{}'", order);
        Payment payment = createPaymentByOrder(order);
        log.debug(">>> Salvando o pagamento '{}'", payment);
        repository.save(payment);
        publish(order);
    }

    private void publish(Order order) {
        BilledOrderEvent event = BilledOrderEvent.builder()
                .transactionId(transactionIdHolder.getCurrentTransactionId())
                .order(order)
                .build();
        log.info(">>> Publicando a conta atraves do evento '{}'", event);
        publisher.publishEvent(event);
    }

    private Payment createPaymentByOrder(Order order) {
        return Payment.builder()
                .paymentStatus(BILLED)
                .valueBilled(order.getValue())
                .orderId(order.getId())
                .build();
    }

    @Transactional
    @Override
    public void refund(Long orderId) {
        log.info(">>> Estornando o pagamento pela ordem '{}'", orderId);
        Optional<Payment> optional = repository.findByOrderId(orderId);
        if (optional.isPresent()) {
            Payment payment = optional.get();
            payment.setPaymentStatus(REFUND);
           repository.save(payment);
           log.info("O Pagamento '{}' foi estornado", payment.getId());
        } else {
            log.error("Nao pode localizar o pagamento da ordem '{}' para estornar!", orderId);
        }
    }

}
