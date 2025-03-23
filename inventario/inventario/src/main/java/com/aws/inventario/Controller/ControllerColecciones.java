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
@CrossOrigin(origins = "http://frontend-inventario.s3-website-us-east-1.amazonaws.com/")
@RestController
@RequestMapping("/api/colecciones")
public class ControllerColecciones {

    private final ServiceColecciones serviceColecciones;

    public ControllerColecciones(ServiceColecciones serviceColecciones){
        this.serviceColecciones = serviceColecciones;
    }

    private ResponseEntity.BodyBuilder cabecerasB(ResponseEntity.BodyBuilder responseBuilder) {
        return responseBuilder
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization")
                .header(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @GetMapping("/*")
    public RedirectView fallbackRedirect() {
        return new RedirectView("/api/colecciones/all");
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<Coleccion>>> obtenerAllColecciones() {
        return serviceColecciones.getAllColecciones()
                .map(colecciones -> cabecerasB(ResponseEntity.ok()).body(colecciones));
    }


    @PostMapping
    public Mono<ResponseEntity<Coleccion>> crearColeccion(@RequestBody Coleccion coleccion) {
        return serviceColecciones.createColeccion(coleccion)
                .map(coll -> cabecerasB(ResponseEntity.ok()).body(coll));
    }

   
    @PutMapping
    public Mono<ResponseEntity<Coleccion>> modificarColeccion(@RequestBody Coleccion coleccion) {
        return serviceColecciones.modifyColeccion(coleccion)
                .map(coll ->
                        cabecerasB(ResponseEntity.ok()).body(coll));
    }

    @DeleteMapping
    public Mono<ResponseEntity<String>> eliminarColeccion(@RequestParam String id_coleccion) {
        return serviceColecciones.eliminarColeccion(id_coleccion)
                .then(Mono.just(cabecerasB(ResponseEntity.ok()).body("204 DELETE (temporal)")));
    }


}
