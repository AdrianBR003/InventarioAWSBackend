package com.aws.inventario.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ControllerRest {

    private final RestTemplate restTemplate;

    private final String apiGatewayUrl = "https://35fjsu9dk2.execute-api.us-east-1.amazonaws.com/productos";

    public ControllerRest() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping
    public ResponseEntity<String> crearProducto(@RequestBody Map<String, String> requestBody) {
        Map<String, String> lambdaBody = new HashMap<>();
        lambdaBody.put("action", "createProduct");
        lambdaBody.put("nombre", requestBody.get("nombre"));
        String response = restTemplate.postForObject(apiGatewayUrl, lambdaBody, String.class);
        return ResponseEntity.ok("Lambda dice: " + response);
    }

    @GetMapping
    public ResponseEntity<String> obtenerProductos() {

        Map<String, String> lambdaBody = new HashMap<>();
        lambdaBody.put("action", "getAllProducts");
        String response = restTemplate.postForObject(apiGatewayUrl, lambdaBody, String.class);

        return ResponseEntity.ok(response);
    }
}
