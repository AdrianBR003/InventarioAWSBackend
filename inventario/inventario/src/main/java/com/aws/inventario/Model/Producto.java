package com.aws.inventario.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignora valores nulos en la respuesta
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producto implements Serializable{

    @JsonProperty("id_producto")
    private String id_producto;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("precio")
    private double precio;
    @JsonProperty("cantidad")
    private int cantidad;

    @Override
    public String toString() {
        return "Producto{" +
                "id_producto='" + id_producto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
