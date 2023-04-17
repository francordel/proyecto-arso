package opiniones.graphql;


import com.coxautodev.graphql.tools.GraphQLRootResolver;

import opiniones.modelo.Opinion;
import opiniones.servicio.IServicioOpiniones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class Query implements GraphQLRootResolver {
    
	private IServicioOpiniones servicio = FactoriaServicios.getServicio(IServicioOpiniones.class);
	
	public Opinion getOpinion(String id) throws RepositorioException, EntidadNoEncontrada {
		return servicio.getOpinion(id);
	}
}