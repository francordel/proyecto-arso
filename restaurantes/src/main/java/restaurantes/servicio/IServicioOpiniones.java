package restaurantes.servicio;

import java.util.List;

import restaurantes.modelo.Restaurante;
import restaurantes.modelo.Valoracion;

public interface IServicioOpiniones {
	
	void crearOpinion(Restaurante restaurante); 

    List<Valoracion> recuperarValoraciones(String identificadorOpinion);
}
