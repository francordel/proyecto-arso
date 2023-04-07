package opiniones.servicio;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import java.util.List;

import opiniones.modelo.Plato;
import opiniones.modelo.Restaurante;
import opiniones.modelo.SitioTuristico;

public interface IServicioRestaurantes {

	/** 
	 * Metodo de alta de un restaurante.
	 */
	String create(String nombre, String codigoPostal, String coordenadas) throws RepositorioException;
	
	/**
	 * Actualiza un restaurante.
	 */
	void update(String id, String nombre, String codigoPostal, String coordenadas) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Obtener sitios turísticos próximos.
	 */
	List<SitioTuristico> getSitiosProximos(String id) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Establecer sitios turísticos destacados.
	 */
	void setSitiosDestacados(String id, List<SitioTuristico> sitios) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Añadir un plato al restaurante.
	 */
	void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Añadir un plato al restaurante.
	 */
	void removePlato(String id, String nombre) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Actualizar un plato del restaurante.
	 */
	void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Recupera una actividad utilizando el identificador. 	
	 */
	Restaurante getRestaurante(String id)  throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Elimina una actividad utilizando el identificador.
	 */
	void removeRestaurante(String id)  throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Retorna un resumen de todas las actividades.	
	 */
	List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException;
	
	
}
