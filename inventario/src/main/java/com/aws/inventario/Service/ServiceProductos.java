package com.aws.inventario.Service;

import com.aws.inventario.Model.Producto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ServiceProductos {

    private final WebClient webClient;

    public ServiceProductos(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://35fjsu9dk2.execute-api.us-east-1.amazonaws.com/productos")
                .build();
    }

    public Mono<List<Producto>> getAllProductos() {
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Producto.class)
                .collectList();
    }

    public Mono<Producto> createProducto(Producto producto) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(producto)
                .retrieve()
                .bodyToMono(Producto.class);
    }

    public Mono<Producto> modifyProducto(Producto producto) {
        return webClient.put()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(producto)
                .retrieve()
                .bodyToMono(Producto.class);
    }

    // Vamos a declarar forzadamente DELETE con HttpMethod, porque con .delete no admite body (porque no es lo usual)
    public Mono<Void> eliminarProducto(String id) {
        return webClient.method(HttpMethod.DELETE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("id_producto", id))
                .retrieve()
                .bodyToMono(Void.class);
    }

}
