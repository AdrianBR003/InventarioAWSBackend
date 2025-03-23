package com.aws.inventario.Service;

import com.aws.inventario.Model.Coleccion;
import com.aws.inventario.Model.Producto;
import com.aws.inventario.Model.Transaccion;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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


    public Mono<Coleccion> createColeccion(Coleccion coleccion) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(coleccion)
                .retrieve()
                .bodyToMono(Coleccion.class);
    }

    public Mono<Coleccion> modifyColeccion(Coleccion coleccion) {
        return webClient.put()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(coleccion)
                .retrieve()
                .bodyToMono(Coleccion.class);
    }

    public Mono<Void> eliminarColeccion(String id) {
        return webClient.method(HttpMethod.DELETE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("id_coleccion", id))
                .retrieve()
                .bodyToMono(Void.class);
    }

}

