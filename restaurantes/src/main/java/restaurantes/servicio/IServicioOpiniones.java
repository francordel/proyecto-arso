package restaurantes.servicio;

import java.util.List;

import restaurantes.modelo.Valoracion;

public interface IServicioOpiniones {
	
	String crearOpinion(String restaurante); 

    List<Valoracion> recuperarValoraciones(String identificadorOpinion);
}
