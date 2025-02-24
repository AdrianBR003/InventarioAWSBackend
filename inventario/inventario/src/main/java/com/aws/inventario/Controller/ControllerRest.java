package com.aws.inventario.Controller;


import com.aws.inventario.Model.Producto;
import com.aws.inventario.Repository.DynamoRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ControllerRest {

    private final DynamoRepository miRepositorio;

    public ControllerRest(DynamoRepository miRepositorio) {
        this.miRepositorio = miRepositorio;
    }

    // Insertar un ítem en DynamoDB
    @PostMapping("/guardar")
    public String guardar(@RequestBody Producto entidad) {
        miRepositorio.guardar(entidad);
        return "Dato guardado en DynamoDB";
    }

    // Obtener un ítem de DynamoDB
    @GetMapping("/obtener/{id}")
    public Producto obtener(@PathVariable String id) {
        return miRepositorio.obtener(id);
    }
}

