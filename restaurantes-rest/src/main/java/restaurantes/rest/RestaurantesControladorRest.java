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
    private IServicioRestaurantes servicio = FactoriaServicios.getServicio(IServicioRestaurantes.class);

    @Context
    private UriInfo uriInfo;

    // 1 // void create(Restaurante restaurante)
    // curl -X POST -H "Content-Type: application/json" -d '{"nombre": "nombre", "codigoPostal": "codigoPostal", "coordenadas": "coordenadas"}' http://localhost:8080/restaurantes
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(Restaurante restaurante) throws RepositorioException {
        String id = servicio.create(restaurante.getNombre(), restaurante.getCodigoPostal(),
                restaurante.getCoordenadas());
        URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
        return Response.created(uri).build();
    }

    // 2 // void update(String id, Restaurante restaurante)
    // curl -X PUT -H "Content-Type: application/json" -d '{"nombre": "nombre", "codigoPostal": "codigoPostal", "coordenadas": "coordenadas"}' http://localhost:8080/restaurantes/{id}
    @PUT
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response update(@PathParam("id") String id, Restaurante restaurante)
            throws RepositorioException, EntidadNoEncontrada {
            //if (restaurante.getNombre().equals("")) {
            //    
            //}
            // CONSULTAR DUDA EN SERVICIO (update)
            servicio.update(id, restaurante.getNombre(), restaurante.getCodigoPostal(), restaurante.getCoordenadas());
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // 3 // List<SitioTuristico> getSitiosProximos(String id)
    // curl -X GET http://localhost:8080/restaurantes/{id}/sitiosProximos
    @GET
    @Path("/{id}/sitiosProximos")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getSitiosProximos(@PathParam("id") String id) throws RepositorioException, EntidadNoEncontrada {
        List<SitioTuristico> sitios = servicio.getSitiosProximos(id);
        return Response.status(Response.Status.OK).entity(sitios).build();
    }

    // 4 // void setSitiosDestacados(String id, List<SitioTuristico> sitios)
    // curl -X PUT -H "Content-Type: application/json" -d '[{"nombre": "nombre", "descripcion": "descripcion"}]' http://localhost:8080/restaurantes/{id}/sitiosDestacados
    @PUT
    @Path("/{id}/sitiosDestacados")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response setSitiosDestacados(@PathParam("id") String id, List<SitioTuristico> sitios)
            throws RepositorioException, EntidadNoEncontrada {
        servicio.setSitiosDestacados(id, sitios);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // 5 // void addPlato(String id, Plato plato)
    // curl -X POST -H "Content-Type: application/json" -d '{"nombre": "nombre", "descripcion": "descripcion"}' http://localhost:8080/restaurantes/{id}/platos
    @POST
    @Path("/{id}/platos")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response addPlato(@PathParam("id") String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {
        servicio.addPlato(id, plato);
        return Response.status(Response.Status.CREATED).build();
    }

    // 6 // void removePlato(String id, String nombre)
    // curl -X DELETE http://localhost:8080/restaurantes/{id}/platos/{nombre}
    @DELETE
    @Path("/{id}/platos/{nombre}")
    public Response removePlato(@PathParam("id") String id, @PathParam("nombre") String nombre)
            throws Exception {

        servicio.removePlato(id, nombre);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // 7 // void updatePlato(String id, Plato plato)
    // curl -X PUT -H "Content-Type: application/json" -d '{"nombre": "nombre", "descripcion": "descripcion"}' http://localhost:8080/restaurantes
    @PUT
    @Path("/{id}/platos")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response updatePlato(@PathParam("id") String id, Plato plato)
            throws RepositorioException, EntidadNoEncontrada {
        servicio.updatePlato(id, plato);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // 8 // Restaurante getRestaurante(String id)
    // curl -X GET http://localhost:8080/restaurantes/{id}
    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRestaurante(@PathParam("id") String id)
            throws Exception {
        return Response.status(Response.Status.OK)
                .entity(servicio.getRestaurante(id)).build();
    }

    // 9 // void removeRestaurante(String id)
    // curl -X DELETE http://localhost:8080/restaurantes/{id}
    @DELETE
    @Path("/{id}")
    public Response removeRestaurante(@PathParam("id") String id)
            throws Exception {

        servicio.removeRestaurante(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // 10 // Listado getListadoRestaurantes()
    // curl -X GET http://localhost:8080/restaurantes
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
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
