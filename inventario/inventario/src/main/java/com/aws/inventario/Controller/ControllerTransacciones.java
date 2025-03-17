package com.aws.inventario.Controller;

import com.aws.inventario.Model.Producto;
import com.aws.inventario.Model.Transaccion;
import com.aws.inventario.Service.ServiceProductos;
import com.aws.inventario.Service.ServiceTransacciones;
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

    @GetMapping("/*")
    public RedirectView fallbackRedirect() {
        return new RedirectView("/api/transacciones/all");
    }

    @GetMapping("/all")
    public Mono<List<Transaccion>> obtenerAllTransacciones() {
        return serviceTransacciones.getAllTransacciones();
    }
}
