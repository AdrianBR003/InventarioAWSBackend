package com.aws.inventario.Controller;

import com.aws.inventario.Model.Producto;
import com.aws.inventario.Service.ServiceProductos;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ControllerProductos {

    private final ServiceProductos serviceProductos;

    public ControllerProductos(ServiceProductos serviceProductos){
        this.serviceProductos = serviceProductos;
    }


    @GetMapping("/all")
    public Mono<List<Producto>> obtenerAllProducto() {
        return serviceProductos.getAllProductos();
    }
}
