package restaurantes.rest;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import restaurantes.rest.Listado.ResumenExtendido;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import restaurantes.servicio.FactoriaServicios;
import restaurantes.servicio.IServicioRestaurantes;
import restaurantes.servicio.RestauranteResumen;

@Path("restaurantes")
public class RestaurantesControladorRest {
    private IServicioRestaurantes servicio =
            FactoriaServicios.getServicio(IServicioRestaurantes.class);
    
	@Context
	private UriInfo uriInfo;
    
    
    // 1
    //	String create(String nombre, String codigoPostal, String coordenadas) throws RepositorioException;
    
	// TODO (estaba mal hecho este método)
    
    // 2
    //	void update(String id, String nombre, String codigoPostal, String coordenadas) throws RepositorioException, EntidadNoEncontrada;

	// TODO (estaba mal hecho este método)

    // 3
    //	List<SitioTuristico> getSitiosProximos(String id) throws RepositorioException, EntidadNoEncontrada;

	// TODO (estaba mal hecho este método)


    // 4
    // 	void setSitiosDestacados(String id, List<SitioTuristico> sitios) throws RepositorioException, EntidadNoEncontrada;
	
	// TODO (estaba mal hecho este método)

    
    // 5
    // 	void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
	
//    @POST
//    @Path("/{id}/platos")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response addPlato(@PathParam("id") String id) 
//    	throws Exception {
//    	
//    	
//    	return ;
//    }
    
    // DUDA: Como se accedería?? Creo que no se puede meter un plato como tal en la URL entonces no puedes usarlo como parámetro.

    
    // 6
    //	void removePlato(String id, String nombre) throws RepositorioException, EntidadNoEncontrada;
    @DELETE
    @Path("/{id}/platos/{nombre}")
    public Response removePlato(@PathParam("id") String id, @PathParam("nombre") String nombre) 
    	throws Exception {
    		
    	servicio.removePlato(id, nombre);
		return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    // 7
    //	void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
    
//    @DELETE
//    @Path("/{id}/platos")
//    public Response updatePlato(@PathParam("id") String id) 
//    	throws Exception {
//    		
//    	return ;
//    }
    
    // DUDA: Como se accedería?? Creo que no se puede meter un plato como tal en la URL entonces no puedes usarlo como parámetro.
    
    // 8
    //	Restaurante getRestaurante(String id)  throws RepositorioException, EntidadNoEncontrada;
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRestaurante(@PathParam("id") String id) 
    	throws Exception {
    
    	return Response.status(Response.Status.OK) 
    				   .entity(servicio.getRestaurante(id)).build();    	
    }
    
    // 9
	// void removeRestaurante(String id)  throws RepositorioException, EntidadNoEncontrada;
    
    @DELETE
    @Path("/{id}")
    public Response removeRestaurante(@PathParam("id") String id) 
    	throws Exception {
    	
    	servicio.removeRestaurante(id);
    	
		return Response.status(Response.Status.NO_CONTENT).build();
    	
    }
    
    // 10
    // 	List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getListadoRestaurantes()
    	throws Exception {
    	
    	List<RestauranteResumen> resultado = servicio.getListadoRestaurantes();
    	
    	LinkedList<ResumenExtendido> extendido = new LinkedList<Listado.ResumenExtendido>();
    	
		for (RestauranteResumen restauranteResumen : resultado) {

			ResumenExtendido resumenExtendido = new ResumenExtendido();

			resumenExtendido.setResumen(restauranteResumen);

			// URL

			String id = restauranteResumen.getId();
			URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();

			resumenExtendido.setUrl(nuevaURL.toString()); // string

			extendido.add(resumenExtendido);

		}
		
		Listado listado = new Listado();
		
		listado.setRestaurante(extendido);
    	
		return Response.ok(listado).build();
   }
    
    
}
