package opiniones.servicio;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioOpiniones implements IServicioOpiniones {
	
	private Repositorio<Opinion, String> repositorio = FactoriaRepositorios.getRepositorio(Opinion.class);

	@Override
	public String create(String nombreRecurso) throws RepositorioException {
		Opinion opinion = new Opinion(nombreRecurso, new LinkedList<>());
		repositorio.add(opinion);
		return opinion.getId();
	}

	@Override
	public boolean addValoracion(String id, Valoracion valoracion) throws RepositorioException, EntidadNoEncontrada {
	    Opinion opinion = repositorio.getById(id);
	    List<Valoracion> valoraciones = opinion.getValoraciones();

	    // Recorremos la lista de valoraciones con un iterador
	    Iterator<Valoracion> iter = valoraciones.iterator();
	    while (iter.hasNext()) {
	        Valoracion v = iter.next();
	        if (v.getCorreoElectronico().equals(valoracion.getCorreoElectronico())) {
	            // Si encontramos una valoración con el mismo correo, la eliminamos
	            iter.remove();
	        }
	    }

	    // Añadimos la nueva valoración a la lista
	    valoraciones.add(valoracion);
	    repositorio.update(opinion);
	    return true;
	}


	@Override
	public Opinion getOpinion(String id) throws RepositorioException, EntidadNoEncontrada {
		return repositorio.getById(id);
	}

	@Override
	public boolean removeOpinion(String id) throws RepositorioException, EntidadNoEncontrada {
		Opinion opinion = repositorio.getById(id);
		repositorio.delete(opinion);
		return true;
	}



}
