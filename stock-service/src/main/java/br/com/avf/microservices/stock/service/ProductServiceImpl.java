package br.com.avf.microservices.stock.service;

import br.com.avf.microservices.stock.domain.Product;
import br.com.avf.microservices.stock.domain.ProductRepository;
import br.com.avf.microservices.stock.events.OrderCanceledEvent;
import br.com.avf.microservices.stock.events.OrderDoneEvent;
import br.com.avf.microservices.stock.exception.StockException;
import br.com.avf.microservices.stock.protocol.Order;
import br.com.avf.microservices.stock.utility.TransactionIdHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;
    private final TransactionIdHolder transactionIdHolder;

    @Transactional
    @Override
    public void updateQuantity(Order order) {
        log.debug("Start updating product {}", order.getProductId());

        Product product = getProduct(order);
        checkStock(order, product);
        updateStock(order, product);

        publishOrderDone(order);
    }

    private void updateStock(Order order, Product product) {
        product.setQuantity(product.getQuantity() - order.getQuantity());
        log.debug("Atualizando o produto '{}' com a quantidade '{}'", product.getId(), product.getQuantity());
        productRepository.save(product);
    }

    private void publishOrderDone(Order order) {
        OrderDoneEvent event = new OrderDoneEvent(transactionIdHolder.getCurrentTransactionId(), order);
        log.debug("Publicando o evento '{}'", event);
        publisher.publishEvent(event);
    }

    private void checkStock(Order order, Product product) {
        log.debug("Checando se tem produtos '{}', produtos ordenados {}", product.getQuantity(), order.getQuantity());
        if (product.getQuantity() < order.getQuantity()) {
            publishCanceledOrder(order);
            throw new StockException("Produto " + product.getId() + " esgotado");
        }
    }

    private void publishCanceledOrder(Order order) {
        OrderCanceledEvent event = new OrderCanceledEvent(transactionIdHolder.getCurrentTransactionId(), order);
        log.debug("Publicando o envento de cancelamento de ordem {}", event);
        publisher.publishEvent(event);
    }

    private Product getProduct(Order order) {
        Optional<Product> optionalProduct = productRepository.findById(order.getProductId());
        return optionalProduct.orElseThrow(() -> {
            publishCanceledOrder(order);
            return new StockException("Nao pode localizar a ordem " + order.getProductId());
        });
    }
}
