package opiniones.servicio;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

/*
 * Esta clase decora el servicio original con las anotaciones GraphQL
 */
public class ServicioOpinionesGraphQL implements IServicioOpiniones {

	private IServicioOpiniones servicio = FactoriaServicios.getServicio(IServicioOpiniones.class);
	
	@GraphQLMutation
	@Override
	public String create(String nombreRecurso) throws RepositorioException {
		return servicio.create(nombreRecurso);
	}

	@GraphQLMutation
	public void addValoracion(String id, Valoracion valoracion) throws RepositorioException, EntidadNoEncontrada {
		servicio.addValoracion(id, valoracion);
		
	}

	@GraphQLQuery
	public Opinion getOpinion(String id) throws RepositorioException, EntidadNoEncontrada {
		return servicio.getOpinion(id);
	}

	@GraphQLMutation
	public void removeOpinion(String id) throws RepositorioException, EntidadNoEncontrada {
		servicio.removeOpinion(id);		
	}

}
