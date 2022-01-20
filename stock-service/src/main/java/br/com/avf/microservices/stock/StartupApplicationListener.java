package br.com.avf.microservices.stock;

import br.com.avf.microservices.stock.domain.Product;
import br.com.avf.microservices.stock.domain.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author angelo.vicente@veolia.com
 */
@Slf4j
@AllArgsConstructor
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ProductRepository productRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Creating default products");
        log.debug("Created product {}", productRepository.save(new Product(3L)));
        log.debug("Created product {}", productRepository.save(new Product(4L)));
        log.debug("Created product {}", productRepository.save(new Product(10L)));
    }
}
