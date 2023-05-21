package restaurantes.servicio;

import java.util.List;

import restaurantes.modelo.Restaurante;
import restaurantes.modelo.Valoracion;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServicioOpiniones implements IServicioOpiniones{

	@Override
	public void crearOpinion(Restaurante restaurante) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5002/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        
		
	}

	@Override
	public List<Valoracion> recuperarValoraciones(String identificadorOpinion) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5002/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
		// TODO Auto-generated method stub
		return null;
	}

}
