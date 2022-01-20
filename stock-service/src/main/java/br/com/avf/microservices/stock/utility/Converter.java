package br.com.avf.microservices.stock.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author angelo.vicente@veolia.com
 */
@AllArgsConstructor
@Component
public class Converter {

    private final ObjectMapper mapper;

    public String toJson(final  Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Nao pode converter "+object+" em json", e);
        }
    }

    public <T> T toObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Nao pode converter "+json+" para um objeto "+clazz.getSimpleName(), e);
        }
    }
}
