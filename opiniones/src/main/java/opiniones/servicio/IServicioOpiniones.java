package opiniones.servicio;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;

public interface IServicioOpiniones {

	/** 
	 * Registrar un recurso (con un nombre) para ser valorado (crea una opinión)
	 */
	String create(String nombreRecurso) throws RepositorioException;

	/**
	 * Añadir una valoración sobre un recurso
	 */
	void addValoracion(String id, Valoracion valoracion) throws RepositorioException, EntidadNoEncontrada;
	
	
	/**
	 * Recuperar la opinión de un recurso utilizando el identificador. 	
	 */
	Opinion getOpinion(String id)  throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Elimina una opinion y sus valoraciones.
	 */
	void removeOpinion(String id)  throws RepositorioException, EntidadNoEncontrada;

	
	
}
 