package restaurantes.rest;

import java.util.List;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantesRestClient {

    @POST("restaurantes")
    Call<Void> createRestaurante(@Body Restaurante restaurante);

    @PUT("restaurantes/{id}")
    Call<Void> updateRestaurante(@Path("id") String id, @Body Restaurante restaurante);

    @GET("restaurantes/{id}/sitiosProximos")
    Call<List<SitioTuristico>> getSitiosProximos(@Path("id") String id);

    @PUT("restaurantes/{id}/sitiosDestacados")
    Call<Void> setSitiosDestacados(@Path("id") String id, @Body List<SitioTuristico> sitios);

    @POST("restaurantes/{id}/platos")
    Call<Void> addPlato(@Path("id") String id, @Body Plato plato);

    @DELETE("restaurantes/{id}/platos/{nombre}")
    Call<Void> removePlato(@Path("id") String id, @Path("nombre") String nombre);

    @PUT("restaurantes/{id}/platos")
    Call<Void> updatePlato(@Path("id") String id, @Body Plato plato);

    @GET("restaurantes/{id}")
    Call<Restaurante> getRestaurante(@Path("id") String id);

    @DELETE("restaurantes/{id}")
    Call<Void> removeRestaurante(@Path("id") String id);

    @GET("restaurantes")
    Call<List<Restaurante>> getListadoRestaurantes();
}
