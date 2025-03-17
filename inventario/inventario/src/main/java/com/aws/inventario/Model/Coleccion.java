package com.aws.inventario.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignora valores nulos en la respuesta
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coleccion implements Serializable{

    @JsonProperty("id_coleccion")
    private String id_producto;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("responsable")
    private String responsable;
}
