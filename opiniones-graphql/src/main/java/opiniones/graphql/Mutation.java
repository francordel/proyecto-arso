package opiniones.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import opiniones.modelo.Valoracion;
import opiniones.servicio.IServicioOpiniones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class Mutation implements GraphQLRootResolver {
    
	private IServicioOpiniones servicio = FactoriaServicios.getServicio(IServicioOpiniones.class);
	
	public String create(String nombreRecurso) throws RepositorioException {
		return servicio.create(nombreRecurso);
	}
	public void addValoracion(String id, String correoElectronico, int calificacion, String comentario) throws RepositorioException, EntidadNoEncontrada {
		servicio.addValoracion(id, new Valoracion(correoElectronico, calificacion,comentario));
		
	}
	public void removeOpinion(String id) throws RepositorioException, EntidadNoEncontrada {
		servicio.removeOpinion(id);		
	}
}
