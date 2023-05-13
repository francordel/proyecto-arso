package restaurantes.servicio;

import java.util.List;

import restaurantes.modelo.Restaurante;
import restaurantes.modelo.Valoracion;

public interface ServicioOpiniones {
	
	void crearOpinion(Restaurante restaurante); 
	//En esta operación se crea una opinión para el restaurante y se inicializan las propiedades anteriores
	/*
	 * numeroDeValoraciones = 0
	 * calificacionMedia = 0
	 * identificadorOpinion = new Id
	 */
    List<Valoracion> recuperarValoraciones(String identificadorOpinion);
}
