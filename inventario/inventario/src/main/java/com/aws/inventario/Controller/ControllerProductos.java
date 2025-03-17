package com.aws.inventario.Controller;

import com.aws.inventario.Model.Coleccion;
import com.aws.inventario.Model.Producto;
import com.aws.inventario.Service.ServiceProductos;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ControllerProductos {

    private final ServiceProductos serviceProductos;

    public ControllerProductos(ServiceProductos serviceProductos){
        this.serviceProductos = serviceProductos;
    }

    // Metodo para añadir las cabeceras a cada respuesta
    private ResponseEntity.BodyBuilder cabeceras(ResponseEntity.BodyBuilder responseBuilder) {
        return responseBuilder
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization")
                .header(HttpHeaders.CONTENT_TYPE, "application/json"); // Asegura que sea JSON
    }

    // Redirige a /all en caso de que la ruta no este controlada
    @GetMapping("/*")
    public RedirectView fallbackRedirect() {
        return new RedirectView("/api/productos/all");
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<Producto>>> obtenerAllProductos() {
        return serviceProductos.getAllProductos()
                .map(productos -> cabeceras(ResponseEntity.ok()).body(productos));
    }
}
