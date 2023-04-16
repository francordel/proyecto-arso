package retrofit.restaurantes;

import java.net.URI;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Programa {
    public static void main(String[] args) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        RestaurantesRestClient service = retrofit.create(RestaurantesRestClient.class);

        // Create a new restaurant
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("My Restaurant");
        restaurante.setCodigoPostal("12345");
        restaurante.setCoordenadas("40.712776, -74.005974");

        Response<Void> createResult = service.create(restaurante).execute();
        String restaurantUrl = createResult.headers().get("Location");
        String restaurantId = restaurantUrl.substring(restaurantUrl.lastIndexOf("/") + 1);

        System.out.println("Restaurante creado: " + restaurantUrl);
        System.out.println("ID: " + restaurantId);

        // Retrieve the restaurant
        Response<Restaurante> response = service.getRestaurante(restaurantId).execute();
        Restaurante retrievedRestaurant = response.body();
        MediaType contentType = MediaType.get(response.headers().get("Content-Type"));

        // Check that the response is in JSON format
        if (!"application/json".equalsIgnoreCase(contentType.toString())) {
            throw new RuntimeException("Formato no esperado: " + contentType);
        }

        System.out.println("Restaurante: " + retrievedRestaurant.getNombre());
        System.out.println("Código Postal: " + retrievedRestaurant.getCodigoPostal());
        System.out.println("Coordenadas: " + retrievedRestaurant.getCoordenadas());

        // Update the restaurant
        retrievedRestaurant.setNombre("Updated Restaurant");
        service.update(restaurantId, retrievedRestaurant).execute();
        System.out.println("Restaurante actualizado");

        // Get list of all restaurants
        Listado listado = service.getListadoRestaurantes().execute().body();
        System.out.println("Listado de restaurantes:");
        for (ResumenExtendido restauranteResumen : listado.getRestaurante()) {
            System.out.println("\t" + restauranteResumen.getResumen().getNombre());
            System.out.println("\t" + restauranteResumen.getUrl());
        }

        // Add a new dish to the restaurant
        Plato plato = new Plato();
        plato.setNombre("Pizza Margherita");
        plato.setDescripcion("Classic Italian pizza with tomato, mozzarella, and basil");

        service.addPlato(restaurantId, plato).execute();
        System.out.println("Plato añadido");

        // Update the dish
        plato.setDescripcion("Updated description for Pizza Margherita");
        service.updatePlato(restaurantId, plato).execute();
        System.out.println("Plato actualizado");

        // Remove the dish from the restaurant
        service.removePlato(restaurantId, plato.getNombre()).execute();
        System.out.println("Plato eliminado");

        // Remove the restaurant
        service.removeRestaurante(restaurantId).execute();
        System.out.println("Restaurante eliminado");

        
        System.out.println("Fin.");
    }
}