package restaurantes.retrofit;

import java.nio.charset.StandardCharsets;
import java.util.List;

import okhttp3.MediaType;
import restaurantes.modelo.Opinion;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.Valoracion;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Programa {
    public static void main(String[] args) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://localhost:7057/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        OpinionesRestClient service = retrofit.create(OpinionesRestClient.class);

        // Recuperar las valoraciones de la opinión
        Response<List<Valoracion>> response = service.recuperarValoraciones("646921dd237000f024d201fa").execute();
        System.out.println(response.headers());
        List<Valoracion> valoraciones = response.body();
        MediaType contentType = MediaType.get(response.headers().get("Content-Type"));

        // Comprobar que la respuesta está en formato JSON
        if (!"application/json".equalsIgnoreCase(contentType.toString())) {
            throw new RuntimeException("Formato no esperado: " + contentType);
        }

        System.out.println("Valoraciones:");
        for (Valoracion valoracion : valoraciones) {
            System.out.println("\tCorreo electrónico: " + valoracion.getCorreoElectronico());
            System.out.println("\tFecha de registro: " + valoracion.getFechaRegistro());
            System.out.println("\tCalificación: " + valoracion.getCalificacion());
            System.out.println("\tComentario: " + valoracion.getComentario());
        }
        
        
        // Crear una nueva opinión
        Restaurante restaurante = new Restaurante();
        // (configurar campos del restaurante según sea necesario)

        Response<Opinion> createResult = service.crearOpinion(restaurante).execute();
        System.out.println(createResult.headers());
        String opinionUrl = createResult.headers().get("Location");
        String rawId = opinionUrl.substring(opinionUrl.lastIndexOf("/") + 1);
        String decodedId = java.net.URLDecoder.decode(rawId, StandardCharsets.UTF_8);
        String opinionId = decodedId.replace("BsonObjectId{value=", "").replace("}", "");

        System.out.println("Opinión creada: " + opinionUrl);
        System.out.println("ID: " + opinionId);

        System.out.println("Fin.");
    }
}
