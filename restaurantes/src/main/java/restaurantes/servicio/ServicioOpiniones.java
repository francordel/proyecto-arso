package restaurantes.servicio;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import restaurantes.modelo.Valoracion;
import restaurantes.retrofit.OpinionResponse;
import restaurantes.retrofit.OpinionesRestClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServicioOpiniones implements IServicioOpiniones{

	@Override
	public String crearOpinion(String restaurante) {
		Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://opiniones:5002/api/")
				//.baseUrl("http://localhost:5047/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
		System.out.println("Cramos MI servicio opinion");
        OpinionesRestClient service = retrofit.create(OpinionesRestClient.class);
        
        try {
        	System.out.println("Mi ID "+restaurante);
			Response<Void> createResult = service.crearOpinion(restaurante).execute();
			System.out.println("Respuesta "+createResult);
			String opinionUrl = createResult.headers().get("Location"); 
			String rawId = opinionUrl.substring(opinionUrl.lastIndexOf("/") + 1);
			System.out.println("Id opinion " + rawId);
			return rawId;
			 	        
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         

		return null;
	}

	@Override
	public List<Valoracion> recuperarValoraciones(String identificadorOpinion) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://opiniones:5002/api/")
			    //.baseUrl("http://localhost:5047/api/")
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();

		System.out.println("Crea MI servicio retrofit");
        OpinionesRestClient service = retrofit.create(OpinionesRestClient.class);	
        List<Valoracion> valoraciones = new LinkedList<>();
        try {
        	System.out.println("Recupera valoracion de la opinion " + identificadorOpinion);
        	Response<OpinionResponse> response = service.recuperarValoraciones(identificadorOpinion).execute();
        	System.out.println("La respuesta optenida es: " + response);
        	valoraciones = response.body().getValoraciones();
			 System.out.println("Valoraciones:");
		        for (Valoracion valoracion : valoraciones) {
		            System.out.println("\tCorreo electrónico: " + valoracion.getCorreoElectronico());
		            System.out.println("\tFecha de registro: " + valoracion.getFechaRegistro());
		            System.out.println("\tCalificación: " + valoracion.getCalificacion());
		            System.out.println("\tComentario: " + valoracion.getComentario());
		        }		
		       } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return valoraciones;
	}



}
