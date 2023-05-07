package bookle.rest;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import bookle.modelo.Actividad;
import bookle.rest.Listado.ResumenExtendido;
import bookle.rest.seguridad.AvailableRoles;
import bookle.rest.seguridad.Secured;
import bookle.servicio.ActividadResumen;
import bookle.servicio.IServicioBookle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import servicio.FactoriaServicios;

@Api
@Path("actividades")
public class BookleControladorRest {

	private IServicioBookle servicio = FactoriaServicios.getServicio(IServicioBookle.class);

	@Context
	private UriInfo uriInfo;
	
	@Context
	private SecurityContext securityContext;
	
	// Ejemplo: http://localhost:8080/api/actividades/1
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Consulta una actividad", notes = "Retorna una actividad utilizando su id", response = Actividad.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Actividad no encontrada") })
	public Response getActividad(@ApiParam(value = "id de la actividad", required = true) @PathParam("id") String id)
			throws Exception {

		return Response.status(Response.Status.OK).entity(servicio.getActividad(id)).build();
	}

	// Utiliza un fichero de prueba disponible en el proyecto
	// curl -i -X POST -H "Content-type: application/xml" -d @test-files/1.xml http://localhost:8080/api/actividades/

	// No hay que agregar ningún fragmento al path

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response create(Actividad actividad) throws Exception {

		String id = servicio.create(actividad);

		URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(nuevaURL).build();
	}

	// Utiliza un fichero de prueba disponible en el proyecto
	// curl -i -X PUT -H "Content-type: application/xml" -d @test-files/1.xml http://localhost:8080/api/actividades/1

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") String id, Actividad actividad) throws Exception {

		if (!id.equals(actividad.getId()))
			throw new IllegalArgumentException("El identificador no coincide: " + id);

		servicio.update(actividad);

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	// curl -i -X DELETE http://localhost:8080/api/actividades/1

	@DELETE
	@Path("/{id}")
	public Response removeActividad(@PathParam("id") String id) throws Exception {

		servicio.removeActividad(id);

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	// Si no se especifica la cabecera "Accept", se retorna en el primer formato (XML)
	// curl http://localhost:8080/api/actividades

	// curl -H "Accept: application/json" http://localhost:8080/api/actividades

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Secured(AvailableRoles.PROFESOR)
	public Response getListadoActividades() throws Exception {

		// Depuración
		System.out.println(this.securityContext.getUserPrincipal().getName());
		
		List<ActividadResumen> resultado = servicio.getListadoActividades();

		LinkedList<ResumenExtendido> extendido = new LinkedList<Listado.ResumenExtendido>();

		for (ActividadResumen actividadResumen : resultado) {

			ResumenExtendido resumenExtendido = new ResumenExtendido();

			resumenExtendido.setResumen(actividadResumen);

			// URL

			String id = actividadResumen.getId();
			URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();

			resumenExtendido.setUrl(nuevaURL.toString()); // string

			extendido.add(resumenExtendido);

		}

		// Una lista no es un documento válido en XML

		// Creamos un documento con un envoltorio

		Listado listado = new Listado();

		listado.setActividad(extendido);

		return Response.ok(listado).build();

	}

	// curl -i -X POST --data "alumno=Pepe&email=pepe@um.es" http://localhost:8080/api/actividades/1/agenda/2023-02-12/turno/1/reserva

	@POST
	@Path("/{id}/agenda/{fecha}/turnos/{indice}/reserva")
	public Response reservar(@PathParam("id") String id, @PathParam("fecha") String fecha,
			@PathParam("indice") int indice, @FormParam("alumno") String alumno, @FormParam("email") String email)
			throws Exception {

		LocalDate fechaDate = null;
		try {
			fechaDate = LocalDate.parse(fecha);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e);
		}

		servicio.reservar(id, fechaDate, indice, alumno, email);

		return Response.status(Response.Status.NO_CONTENT).build();

	}

}
