package restaurantes.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import restaurantes.modelo.Valoracion;
import restaurantes.rest.Listado.ResumenExtendido;
import restaurantes.rest.seguridad.AvailableRoles;
import restaurantes.rest.seguridad.Secured;
import restaurantes.retrofit.ValoracionesResponse;
import restaurantes.servicio.FactoriaServicios;
import restaurantes.servicio.IServicioRestaurantes;
import restaurantes.servicio.RestauranteResumen;

@Api
@Path("restaurantes")
public class RestaurantesControladorRest {

	private IServicioRestaurantes servicio;
	
	@Context
	private UriInfo uriInfo;

	@Context
	HttpHeaders headers;

	public RestaurantesControladorRest() {
		try {
			this.servicio = FactoriaServicios.getServicio(IServicioRestaurantes.class);
			//this.servicio.subscribeToRabbitMQ();
		} catch (Exception e) {
		}
	}

	@Context
	private SecurityContext securityContext;

	// 1 // void create(Restaurante restaurante)
	// curl -X POST -H "Content-Type: application/json" -d '{"nombre": "nombre",
	// "codigoPostal": "codigoPostal", "coordenadas": "coordenadas"}'
	// http://localhost:8080/restaurantes

	@ApiOperation(value = "Crea un restaurante")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Restaurante creado correctamente"), })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Secured(AvailableRoles.GESTOR)
	public Response create(@ApiParam(value = "Restaurante a crear", required = true) Restaurante restaurante)
			throws RepositorioException {

		String id = servicio.create(restaurante.getNombre(), restaurante.getCodigoPostal(),
				restaurante.getCoordenadas(), this.securityContext.getUserPrincipal().getName(), restaurante.getCiudad());

		// Get headers
		MultivaluedMap<String, String> allHeaders = headers.getRequestHeaders();
		String forwardedHost = allHeaders.getFirst("X-Forwarded-Host");
		String forwardedProto = allHeaders.getFirst("X-Forwarded-Proto");

		// Build URI
		UriBuilder builder = UriBuilder.fromPath("/");
		if (forwardedProto != null) {
			builder.scheme(forwardedProto);
		}
		if (forwardedHost != null) {
			builder.host(forwardedHost);
		}
		builder.path("/restaurantes/" + id);
		URI uri = builder.build();

		return Response.created(uri).build();
	}

	// 2 // void update(String id, Restaurante restaurante)
	// curl -X PUT -H "Content-Type: application/json" -d '{"nombre": "nombre",
	// "codigoPostal": "codigoPostal", "coordenadas": "coordenadas"}'
	// http://localhost:8080/restaurantes/{id}

	@ApiOperation(value = "Actualiza un restaurante")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Restaurante actualizado correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado"), })
	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Secured(AvailableRoles.GESTOR)
	public Response update(
			@ApiParam(value = "ID del restaurante a actualizar", required = true) @PathParam("id") String id,
			@ApiParam(value = "Restaurante con datos actualizados", required = true) Restaurante restaurante)
			throws RepositorioException, EntidadNoEncontrada {

			if (servicio.isGestor(id, this.securityContext.getUserPrincipal().getName())) {
				servicio.update(id, restaurante.getNombre(), restaurante.getCodigoPostal(), restaurante.getCoordenadas(), restaurante.getCiudad());
			}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// 3 // List<SitioTuristico> getSitiosProximos(String id)
	// curl -X GET http://localhost:8080/restaurantes/{id}/sitiosProximos

	@ApiOperation(value = "Obtiene sitios turísticos próximos a un restaurante")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Sitios turísticos obtenidos correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado"), })
	@GET
	@Path("/{id}/sitiosProximos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSitiosProximos(
			@ApiParam(value = "ID del restaurante para buscar sitios turísticos próximos", required = true) @PathParam("id") String id)
			throws RepositorioException, EntidadNoEncontrada {
		List<SitioTuristico> sitios = servicio.getSitiosProximos(id);
		return Response.status(Response.Status.OK).entity(sitios).build();
	}

	// 4 // void setSitiosDestacados(String id, List<SitioTuristico> sitios)
	// curl -X PUT -H "Content-Type: application/json" -d '[{"nombre": "nombre",
	// "descripcion": "descripcion"}]'
	// http://localhost:8080/restaurantes/{id}/sitiosDestacados

	@ApiOperation(value = "Establece sitios turísticos destacados para un restaurante")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Sitios turísticos destacados establecidos correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado"), })
	@PUT
	@Path("/{id}/sitiosDestacados")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Secured(AvailableRoles.GESTOR)
	public Response setSitiosDestacados(
			@ApiParam(value = "ID del restaurante para establecer sitios turísticos destacados", required = true) @PathParam("id") String id,
			@ApiParam(value = "Lista de sitios turísticos destacados", required = true) List<SitioTuristico> sitios)
			throws RepositorioException, EntidadNoEncontrada {
		
		if (servicio.isGestor(id, this.securityContext.getUserPrincipal().getName())) {
			servicio.setSitiosDestacados(id, sitios);
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// 5 // void addPlato(String id, Plato plato)
	// curl -X POST -H "Content-Type: application/json" -d '{"nombre": "nombre",
	// "descripcion": "descripcion"}' http://localhost:8080/restaurantes/{id}/platos

	@ApiOperation(value = "Añade un plato a un restaurante")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Plato añadido correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado"), })
	@POST
	@Path("/{id}/platos")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Secured(AvailableRoles.GESTOR)
	public Response addPlato(
			@ApiParam(value = "ID del restaurante al que añadir el plato", required = true) @PathParam("id") String id,
			@ApiParam(value = "Plato a añadir", required = true) Plato plato)
			throws RepositorioException, EntidadNoEncontrada {
		if (servicio.isGestor(id, this.securityContext.getUserPrincipal().getName())) {
			servicio.addPlato(id, plato);
		}
		return Response.status(Response.Status.CREATED).build();
	}

	// 6 // void removePlato(String id, String nombre)
	// curl -X DELETE http://localhost:8080/restaurantes/{id}/platos/{nombre}

	@ApiOperation(value = "Elimina un plato de un restaurante")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Plato eliminado correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante o plato no encontrado"), })
	@DELETE
	@Path("/{id}/platos/{nombre}")
	@Secured(AvailableRoles.GESTOR)
	public Response removePlato(
			@ApiParam(value = "ID del restaurante del que eliminar el plato", required = true) @PathParam("id") String id,
			@ApiParam(value = "Nombre del plato a eliminar", required = true) @PathParam("nombre") String nombre)
			throws Exception {
		if (servicio.isGestor(id, this.securityContext.getUserPrincipal().getName())) {
			servicio.removePlato(id, nombre);
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// 7 // void updatePlato(String id, Plato plato)
	// curl -X PUT -H "Content-Type: application/json" -d '{"nombre": "nombre",
	// "descripcion": "descripcion"}' http://localhost:8080/restaurantes

	@ApiOperation(value = "Actualiza un plato de un restaurante")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Plato actualizado correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante o plato no encontrado"), })
	@PUT
	@Path("/{id}/platos")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Secured(AvailableRoles.GESTOR)
	public Response updatePlato(
			@ApiParam(value = "ID del restaurante del que actualizar el plato", required = true) @PathParam("id") String id,
			@ApiParam(value = "Plato con datos actualizados", required = true) Plato plato)
			throws RepositorioException, EntidadNoEncontrada {
		if (servicio.isGestor(id, this.securityContext.getUserPrincipal().getName())) {
			servicio.updatePlato(id, plato);
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// 8 // Restaurante getRestaurante(String id)
	// curl -X GET http://localhost:8080/restaurantes/{id}

	@ApiOperation(value = "Obtiene un restaurante por ID")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Restaurante obtenido correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado"), })
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurante(
			@ApiParam(value = "ID del restaurante a obtener", required = true) @PathParam("id") String id)
			throws Exception {
		return Response.status(Response.Status.OK).entity(servicio.getRestaurante(id)).build();
	}

	// 9 // void removeRestaurante(String id)
	// curl -X DELETE http://localhost:8080/restaurantes/{id}

	@ApiOperation(value = "Elimina un restaurante por ID")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Restaurante eliminado correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado"), })
	@DELETE
	@Path("/{id}")
	@Secured(AvailableRoles.GESTOR)
	public Response removeRestaurante(
			@ApiParam(value = "ID del restaurante a eliminar", required = true) @PathParam("id") String id)
			throws Exception {
		if (servicio.isGestor(id, this.securityContext.getUserPrincipal().getName())) {
			servicio.removeRestaurante(id);
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// 10 // Listado getListadoRestaurantes()
	// curl -X GET http://localhost:8080/restaurantes
	@ApiOperation(value = "Obtiene el listado de restaurantes")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Listado de restaurantes obtenido correctamente"), })
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getListadoRestaurantes() throws Exception {
		List<RestauranteResumen> resultado = servicio.getListadoRestaurantes();
		LinkedList<ResumenExtendido> extendido = new LinkedList<Listado.ResumenExtendido>();
		for (RestauranteResumen restauranteResumen : resultado) {
			ResumenExtendido resumenExtendido = new ResumenExtendido();
			resumenExtendido.setResumen(restauranteResumen);
			String id = restauranteResumen.getId();
			URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();
			resumenExtendido.setUrl(nuevaURL.toString());
			extendido.add(resumenExtendido);
		}
		Listado listado = new Listado();
		listado.setRestaurante(extendido);
		return Response.ok(listado).build();
	}
	
	//2 METODOS
	
	//EL DE CREAR OPINION

	@ApiOperation(value = "Crea una opinion")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Opinion creada correctamente"),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado"), })
	@Path("/{id}/opinion")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response crearOpinion(@ApiParam(value = "ID del restaurante para crear opinion", required = true) @PathParam("id") String id) throws RepositorioException, EntidadNoEncontrada {
		System.out.println("Crear opinion");
		servicio.crearOpinion(id);
		return Response.status(Response.Status.CREATED).build();
		
	}
	//EL DE GET VALORACIONES

	@ApiOperation(value = "Obtiene el listado de valoraciones")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Listado de restaurantes obtenido correctamente"), })
	@Path("/{id}/valoraciones")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getValoraciones(@ApiParam(value = "ID del restaurante para obtener valoraciones", required = true) @PathParam("id") String id) throws RepositorioException, EntidadNoEncontrada {
		System.out.println("Rest obteniendo valoraciones...");
		List<Valoracion> valoraciones = servicio.getValoraciones(id);
		System.out.println("Valoraciones obtenidas: " + valoraciones);
		return Response.ok(valoraciones).build();
	}
}