package com.aws.lambda_inventario;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductosLambdaHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayProxyResponseEvent> {

    private static final String TABLE_NAME = "TablaPrueba";
    private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(Region.US_EAST_1).build();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson para JSON

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayV2HTTPEvent request, Context context) {
        try {
            // Verificar si la solicitud es nula
            if (request == null) {
                context.getLogger().log("ERROR: La solicitud recibida es NULL");
                return createResponse(500, "Error: La solicitud recibida es NULL");
            }

            // Extraer el método HTTP de forma correcta para API Gateway v2
            String httpMethod = "UNKNOWN";
            if (request.getRequestContext() != null &&
                    request.getRequestContext().getHttp() != null &&
                    request.getRequestContext().getHttp().getMethod() != null) {
                httpMethod = request.getRequestContext().getHttp().getMethod();
            }

            // Log para ver qué método se está recibiendo
            context.getLogger().log("🔎 Método HTTP recibido: " + httpMethod);

            // Verificar qué tipo de operación se debe realizar
            switch (httpMethod) {
                case "POST":
                    return createProduct(request.getBody(), context);
                case "GET":
                    return getAllProducts(context);
                default:
                    return createResponse(400, "Método HTTP no soportado");
            }
        } catch (Exception e) {
            context.getLogger().log("ERROR en handleRequest: " + e.getMessage());
            return createResponse(500, "Error interno: " + e.getMessage());
        }
    }

    private APIGatewayProxyResponseEvent createProduct(String body, Context context) {
        try {
            context.getLogger().log("Cuerpo recibido en POST: " + body);

            if (body == null || body.trim().isEmpty()) {
                return createResponse(400, "El cuerpo de la solicitud está vacío.");
            }

            Producto producto = objectMapper.readValue(body, Producto.class); // Convertir JSON a objeto

            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                return createResponse(400, "Falta el campo 'nombre'.");
            }

            producto.setId(UUID.randomUUID().toString());

            Map<String, AttributeValue> item = new HashMap<>();
            item.put("id", AttributeValue.builder().s(producto.getId()).build());
            item.put("nombre", AttributeValue.builder().s(producto.getNombre()).build());

            dynamoDbClient.putItem(PutItemRequest.builder().tableName(TABLE_NAME).item(item).build());

            context.getLogger().log("Producto creado con ID: " + producto.getId());

            return createResponse(200, objectMapper.writeValueAsString(producto));
        } catch (Exception e) {
            context.getLogger().log("ERROR en createProduct: " + e.getMessage());
            return createResponse(500, "Error al crear producto: " + e.getMessage());
        }
    }

    private APIGatewayProxyResponseEvent getAllProducts(Context context) {
        try {
            context.getLogger().log("Consultando DynamoDB en la tabla: " + TABLE_NAME);

            ScanResponse scanResponse = dynamoDbClient.scan(ScanRequest.builder().tableName(TABLE_NAME).build());

            if (scanResponse == null || scanResponse.items() == null) {
                context.getLogger().log("ERROR: La consulta a DynamoDB devolvió un resultado nulo.");
                return createResponse(500, "Error: La consulta a DynamoDB no devolvió datos.");
            }

            List<Map<String, AttributeValue>> items = scanResponse.items();
            context.getLogger().log("Datos obtenidos de DynamoDB: " + items);

            // Validar que los datos no sean nulos antes de convertirlos a JSON
            if (items.isEmpty()) {
                return createResponse(200, "[]"); // Devolver un array vacío en JSON si no hay productos
            }

            // Convertir datos manualmente para evitar errores de serialización
            StringBuilder responseJson = new StringBuilder("[");
            for (Map<String, AttributeValue> item : items) {
                String id = item.getOrDefault("id", AttributeValue.builder().s("").build()).s();
                String nombre = item.getOrDefault("nombre", AttributeValue.builder().s("Sin nombre").build()).s();

                responseJson.append("{\"id\":\"").append(id).append("\",")
                        .append("\"nombre\":\"").append(nombre).append("\"},");
            }

            if (!items.isEmpty()) {
                responseJson.setLength(responseJson.length() - 1); // Quitar la última coma
            }
            responseJson.append("]");

            return createResponse(200, responseJson.toString());
        } catch (Exception e) {
            context.getLogger().log("ERROR en getAllProducts: " + e.getMessage());
            return createResponse(500, "Error al obtener productos: " + e.getMessage());
        }
    }

    private APIGatewayProxyResponseEvent createResponse(int statusCode, String body) {
        return new APIGatewayProxyResponseEvent().withStatusCode(statusCode).withBody(body);
    }

    public static class Producto {
        private String id;
        private String nombre;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
}
