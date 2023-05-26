package restaurantes.retrofit;


import retrofit2.Call;
import retrofit2.http.*;

public interface OpinionesRestClient {
	
	@FormUrlEncoded
    @POST("opiniones")
    Call<Void> crearOpinion(@Field("nombreRecurso") String nombreRecurso);
    @GET("opiniones/{id}")
    Call<ValoracionesResponse> recuperarValoraciones(@Path("id") String identificadorOpinion);
}
