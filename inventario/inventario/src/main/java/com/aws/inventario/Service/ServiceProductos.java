package com.aws.inventario.Service;

import com.aws.inventario.Model.Producto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

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
                .bodyToFlux(Producto.class) // ✅ Deserializar como Flux de Producto
                .collectList(); // ✅ Convertirlo a una lista
    }
}
