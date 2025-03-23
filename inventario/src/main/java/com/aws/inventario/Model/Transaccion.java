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
public class Transaccion implements Serializable{

    @JsonProperty("id_transaccion")
    private String id_transaccion;
    @JsonProperty("coleccionOrigen")
    private String coleccionOrigen;
    @JsonProperty("coleccionDestino")
    private String coleccionDestino;
    @JsonProperty("producto")
    private String producto;
    @JsonProperty("cantidad")
    private int cantidad;
    @JsonProperty("fecha")
    private String fecha;
}
