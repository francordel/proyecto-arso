package restaurantes.servicio;

import java.util.List;

import restaurantes.modelo.Opinion;
import restaurantes.modelo.Valoracion;

public interface IServicioOpiniones {
	
	void crearOpinion(Opinion opinion); 

    List<Valoracion> recuperarValoraciones(String identificadorOpinion);
}
