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

@CrossOrigin(origins = "http://frontend-inventario.s3-website-us-east-1.amazonaws.com/")
@RestController
@RequestMapping("/api/transacciones")
public class ControllerTransacciones {

    private final ServiceTransacciones serviceTransacciones;

    public ControllerTransacciones(ServiceTransacciones serviceTransacciones){
        this.serviceTransacciones = serviceTransacciones;
    }

    // Metodo para añadir las cabeceras a cada respuesta
    private ResponseEntity.BodyBuilder cabecerasB(ResponseEntity.BodyBuilder responseBuilder) {
        return responseBuilder
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization")
                .header(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @GetMapping("/*")
    public RedirectView fallbackRedirect() {
        return new RedirectView("/api/transacciones/all");
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<Transaccion>>> obtenerAllTransacciones() {
        return serviceTransacciones.getAllTransacciones()
                .map(transacciones -> cabecerasB(ResponseEntity.ok()).body(transacciones));
    }

    @PostMapping
    public Mono<ResponseEntity<Transaccion>> crearTransaccion(@RequestBody Transaccion transaccion) {
        return serviceTransacciones.createTransaccion(transaccion)
                .map(trans -> cabecerasB(ResponseEntity.ok()).body(trans));
    }

    @PutMapping
    public Mono<ResponseEntity<Transaccion>> modificarTransaccion(@RequestBody Transaccion transaccion) {
        return serviceTransacciones.modifyTransaccion(transaccion)
                .map(trans ->
                        cabecerasB(ResponseEntity.ok()).body(trans));
    }

    @DeleteMapping
    public Mono<ResponseEntity<String>> eliminarTransaccion(@RequestParam String id_transaccion) {
        return serviceTransacciones.eliminarTransaccion(id_transaccion)
                .then(Mono.just(cabecerasB(ResponseEntity.ok()).body("204 DELETE (temporal)")));
    }

}
