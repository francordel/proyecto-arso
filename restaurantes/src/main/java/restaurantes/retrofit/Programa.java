package restaurantes.retrofit;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


import okhttp3.MediaType;
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
        
        // (configurar campos del restaurante según sea necesario)

        System.out.println("Creando mensaje...");
    
        Response<Void> createResult = service.crearOpinion("tgb").execute();
        
        System.out.println(createResult);

        System.out.println(createResult.headers());
		
		 String opinionUrl = createResult.headers().get("Location"); 
	     System.out.println("Opinión creada: " + opinionUrl);

		 String rawId = opinionUrl.substring(opinionUrl.lastIndexOf("/") + 1);
		
		 
        
        System.out.println("ID" + rawId);
        
        //System.out.println("ID: " + opinionId);
        
        // Recuperar las valoraciones de la opinión
        Response<OpinionResponse> response = service.recuperarValoraciones("646a80e16f128f2385aa20ca").execute();
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
