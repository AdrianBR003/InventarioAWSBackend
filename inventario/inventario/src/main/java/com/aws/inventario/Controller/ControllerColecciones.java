package com.aws.inventario.Controller;

import com.aws.inventario.Model.Coleccion;
import com.aws.inventario.Model.Producto;
import com.aws.inventario.Service.ServiceColecciones;
import com.aws.inventario.Service.ServiceProductos;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/colecciones")
public class ControllerColecciones {

    private final ServiceColecciones serviceColecciones;

    public ControllerColecciones(ServiceColecciones serviceColecciones){
        this.serviceColecciones = serviceColecciones;
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
        return new RedirectView("/api/colecciones/all");
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<Coleccion>>> obtenerAllColecciones() {
        return serviceColecciones.getAllColecciones()
                .map(colecciones -> cabeceras(ResponseEntity.ok()).body(colecciones));
    }
}
