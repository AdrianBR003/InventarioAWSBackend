package com.aws.inventario.Service;

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
public class ServiceTransacciones {

    private final WebClient webClient;

    public ServiceTransacciones(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://35fjsu9dk2.execute-api.us-east-1.amazonaws.com/transacciones")
                .build();
    }

    public Mono<List<Transaccion>> getAllTransacciones() {
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Transaccion.class)
                .collectList();
    }
}

