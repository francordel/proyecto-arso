package restaurantes.retrofit;

import java.util.List;

import restaurantes.modelo.Opinion;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.Valoracion;
import retrofit2.Call;
import retrofit2.http.*;

public interface OpinionesRestClient {
	
    @POST("api/opiniones")
    Call<Opinion> crearOpinion(@Body Restaurante restaurante);

    @GET("api/opiniones/{id}")
    Call<List<Valoracion>> recuperarValoraciones(@Path("id") String identificadorOpinion);

}
