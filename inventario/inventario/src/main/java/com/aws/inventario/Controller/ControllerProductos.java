package com.aws.inventario.Controller;

import com.aws.inventario.Model.Producto;
import com.aws.inventario.Service.ServiceProductos;
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

    // Redirige a /all en caso de que la ruta no este controlada
    @GetMapping("/*")
    public RedirectView fallbackRedirect() {
        return new RedirectView("/api/productos/all");
    }

    @GetMapping("/all")
    public Mono<List<Producto>> obtenerAllProductos() {
        return serviceProductos.getAllProductos();
    }
}
