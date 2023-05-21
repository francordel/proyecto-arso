package restaurantes.retrofit;

import java.util.List;

import restaurantes.modelo.Opinion;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.Valoracion;
import retrofit2.Call;
import retrofit2.http.*;

public interface OpinionesRestClient {
	
	@FormUrlEncoded
    @POST("opiniones")
    Call<Void> crearOpinion(@Field("nombreRecurso") String nombreRecurso);
    @GET("opiniones/{id}")
    Call<ValoracionesResponse> recuperarValoraciones(@Path("id") String identificadorOpinion);
}
