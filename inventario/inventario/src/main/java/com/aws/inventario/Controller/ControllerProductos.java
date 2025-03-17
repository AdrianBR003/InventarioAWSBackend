package com.aws.inventario.Controller;

import com.aws.inventario.Model.Producto;
import com.aws.inventario.Service.ServiceProductos;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.util.List;


@CrossOrigin(origins = "http://frontend-inventario.s3-website-us-east-1.amazonaws.com/")
@RestController
@RequestMapping("/api/productos")
public class ControllerProductos {

    private final ServiceProductos serviceProductos;

    public ControllerProductos(ServiceProductos serviceProductos){
        this.serviceProductos = serviceProductos;
    }

    // Metodo para añadir las cabeceras a cada respuesta (con parametros en el body,  PROVISIONAL)
    private ResponseEntity.BodyBuilder cabecerasB(ResponseEntity.BodyBuilder responseBuilder) {
        return responseBuilder
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization")
                .header(HttpHeaders.CONTENT_TYPE, "application/json");
    }
//
//    // Metodo para añadir las cabeceras a cada respuesta (con parametros en los headers, en vez de .body, )
//    private ResponseEntity.HeadersBuilder<?> cabecerasC(ResponseEntity.HeadersBuilder<?> responseBuilder) {
//        return responseBuilder
//                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000")
//                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS")
//                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization")
//                .header(HttpHeaders.CONTENT_TYPE, "application/json");
//    }


    // Redirige a /all en caso de que la ruta no este controlada
    @GetMapping("/*")
    public RedirectView fallbackRedirect() {
        return new RedirectView("/api/productos/all");
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<Producto>>> obtenerAllProductos() {
        return serviceProductos.getAllProductos()
                .map(productos -> cabecerasB(ResponseEntity.ok()).body(productos)); // Respuesta OK (200)
    }

    @PostMapping
    public Mono<ResponseEntity<Producto>> crearProducto(@RequestBody Producto producto) { // Recibe el producto en el body
        return serviceProductos.createProducto(producto)
                .map(prod -> cabecerasB(ResponseEntity.ok()).body(prod));
    }

    /*
       NOTA: El mensaje de respuesta de lambda, se ignora en el backend, por lo que para consultarlo lo mejor es mirarlo con POSTMAN
    */
    @PutMapping
    public Mono<ResponseEntity<Producto>> modificarProducto(@RequestBody Producto producto) {
        return serviceProductos.modifyProducto(producto)
                .map(prod ->
                        cabecerasB(ResponseEntity.ok()).body(prod));
    }

    // DELETE no admite información en el body, por lo que sí o sí los parametros se han de pasar por el header, lo que provoca un error en el metodo cabeceras
    @DeleteMapping
    public Mono<ResponseEntity<String>> eliminarProducto(@RequestParam String id_producto) {
        return serviceProductos.eliminarProducto(id_producto)
                .then(Mono.just(cabecerasB(ResponseEntity.ok()).body("204 DELETE (temporal)"))); // Devuelve en el cuerpo el mensaje.
    }

}
