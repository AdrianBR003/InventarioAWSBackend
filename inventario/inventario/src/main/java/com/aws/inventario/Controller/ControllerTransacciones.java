package com.aws.inventario.Controller;

import com.aws.inventario.Model.Coleccion;
import com.aws.inventario.Model.Producto;
import com.aws.inventario.Model.Transaccion;
import com.aws.inventario.Service.ServiceProductos;
import com.aws.inventario.Service.ServiceTransacciones;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class ControllerTransacciones {

    private final ServiceTransacciones serviceTransacciones;

    public ControllerTransacciones(ServiceTransacciones serviceTransacciones){
        this.serviceTransacciones = serviceTransacciones;
    }

    // Metodo para añadir las cabeceras a cada respuesta
    private ResponseEntity.BodyBuilder cabeceras(ResponseEntity.BodyBuilder responseBuilder) {
        return responseBuilder
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization")
                .header(HttpHeaders.CONTENT_TYPE, "application/json"); // Asegura que sea JSON
    }

    @GetMapping("/*")
    public RedirectView fallbackRedirect() {
        return new RedirectView("/api/transacciones/all");
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<Transaccion>>> obtenerAllTransacciones() {
        return serviceTransacciones.getAllTransacciones()
                .map(transacciones -> cabeceras(ResponseEntity.ok()).body(transacciones));
    }
}
