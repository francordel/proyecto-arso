package restaurantes.retrofit;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import restaurantes.modelo.Opinion;
import restaurantes.modelo.Valoracion;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Programa {
    public static void main(String[] args) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5002/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        OpinionesRestClient service = retrofit.create(OpinionesRestClient.class);
        Valoracion valoracion1 = new Valoracion("correo1@example.com", LocalDate.now(), 4, "Muy buen servicio");
        Valoracion valoracion2 = new Valoracion("correo2@example.com", LocalDate.now(), 4, "Bueno, pero puede mejorar");
        Valoracion valoracion3 = new Valoracion("correo3@example.com", LocalDate.now(), 3, "Promedio");

        List<Valoracion> valoraciones = new LinkedList<>();
        valoraciones.add(valoracion1);
        valoraciones.add(valoracion2);
        valoraciones.add(valoracion3);
        // Crear una nueva opinión
        Opinion opinion = new Opinion("tagliatella", valoraciones );
        
        // (configurar campos del restaurante según sea necesario)
        System.out.println(new ObjectMapper().writeValueAsString(opinion));

        System.out.println("Creando mensaje...");
        Response<Opinion> createResult = service.crearOpinion(opinion).execute();
        
        System.out.println(createResult);

        System.out.println(createResult.headers());
        String opinionUrl = createResult.headers().get("Location");
        String rawId = opinionUrl.substring(opinionUrl.lastIndexOf("/") + 1);
        String decodedId = java.net.URLDecoder.decode(rawId);
        String opinionId = decodedId.replace("BsonObjectId{value=", "").replace("}", "");
        
        System.out.println("Opinión creada: " + opinionUrl);
        System.out.println("ID: " + opinionId);

        // Recuperar las valoraciones de la opinión
        System.out.println("Huyendo en el tranvia...");
        Response<ValoracionesResponse> response = service.recuperarValoraciones(opinionId).execute();
        List<Valoracion> valoraciones1 = response.body().getValoraciones();
        System.out.println(response.headers());
        System.out.println(response);

        MediaType contentType = MediaType.get(response.headers().get("Content-Type"));
        // Comprobar que la respuesta está en formato JSON
        if (!contentType.toString().startsWith("application/json")) {
            throw new RuntimeException("Formato no esperado: " + contentType);
        }



        System.out.println("Valoraciones:");
        for (Valoracion valoracion : valoraciones1) {
            System.out.println("\tCorreo electrónico: " + valoracion.getCorreoElectronico());
            System.out.println("\tFecha de registro: " + valoracion.getFechaRegistro());
            System.out.println("\tCalificación: " + valoracion.getCalificacion());
            System.out.println("\tComentario: " + valoracion.getComentario());
        }

    }
}
