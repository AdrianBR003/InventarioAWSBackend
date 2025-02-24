package com.aws.inventario.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/productos")
public class ControllerRest {

    private final RestTemplate restTemplate;
    private final String apiGatewayUrl = "https://35fjsu9dk2.execute-api.us-east-1.amazonaws.com/productos";

    public ControllerRest() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> crearProducto(@RequestBody ProductoRequest requestBody) {
        // Enviar la petición POST a la API Gateway
        String response = restTemplate.postForObject(apiGatewayUrl, requestBody, String.class);
        // Devolver directamente el JSON recibido de Lambda, con el header correcto
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> obtenerProductos() {
        // Enviar la petición GET a la API Gateway
        String response = restTemplate.getForObject(apiGatewayUrl, String.class);
        // Devolver directamente el JSON recibido de Lambda, con el header correcto
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(response);
    }

    // Clase auxiliar para el request de creación de producto
    public static class ProductoRequest {
        private String nombre;

        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
