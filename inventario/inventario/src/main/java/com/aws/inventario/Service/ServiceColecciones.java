package com.aws.inventario.Service;

import com.aws.inventario.Model.Coleccion;
import com.aws.inventario.Model.Producto;
import com.aws.inventario.Model.Transaccion;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ServiceColecciones {

    private final WebClient webClient;

    public ServiceColecciones(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://35fjsu9dk2.execute-api.us-east-1.amazonaws.com/colecciones")
                .build();
    }

    public Mono<List<Coleccion>> getAllColecciones() {
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Coleccion.class)
                .collectList();
    }
}

