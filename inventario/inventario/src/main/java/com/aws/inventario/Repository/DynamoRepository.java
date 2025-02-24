package com.aws.inventario.Repository;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.aws.inventario.Model.Producto;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public DynamoRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void guardar(Producto entidad) {
        dynamoDBMapper.save(entidad);
    }

    public Producto obtener(String id) {
        return dynamoDBMapper.load(Producto.class, id);
    }
}
