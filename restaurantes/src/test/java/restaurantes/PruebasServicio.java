package restaurantes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import restaurantes.servicio.RestauranteResumen;
import restaurantes.servicio.ServicioRestaurantes;

class ServicioRestaurantesTest {
    private ServicioRestaurantes servicioRestaurantes;
    private Restaurante restaurante;
    private SitioTuristico sitioTuristico;

    @BeforeEach
    void setUp() throws RepositorioException, EntidadNoEncontrada {
        servicioRestaurantes = new ServicioRestaurantes();
        String id = servicioRestaurantes.create("Test Restaurante", "12345", "1,1");
        restaurante = servicioRestaurantes.getRestaurante(id);
        
        sitioTuristico = new SitioTuristico();
        sitioTuristico.setTitulo("Test Sitio Turistico");
    }

    @Test
    void create() {
        assertNotNull(restaurante);
        assertEquals("Test Restaurante", restaurante.getNombre());
    }

    @Test
    void update() throws RepositorioException, EntidadNoEncontrada {
    	System.out.println("id update:" + restaurante.getId());
    	servicioRestaurantes.update(restaurante.getId(), "Nuevo Nombre", "54321", "5,5");
        Restaurante restauranteActualizado = servicioRestaurantes.getRestaurante(restaurante.getId());
        assertEquals("Nuevo Nombre", restauranteActualizado.getNombre());
    }

    @Test
    void addPlato() throws RepositorioException, EntidadNoEncontrada {
        Plato plato = new Plato();
        plato.setNombre("Test Plato");
        System.out.println("id addplato:" + restaurante.getId());
        servicioRestaurantes.addPlato(restaurante.getId(), plato);
    }

    @Test
    void removePlato() throws RepositorioException, EntidadNoEncontrada {
        Plato plato = new Plato();
        plato.setNombre("Test Plato");
        System.out.println("id rmplato:" + restaurante.getId());
        servicioRestaurantes.addPlato(restaurante.getId(), plato);
        servicioRestaurantes.removePlato(restaurante.getId(), plato.getNombre());
    }

    @Test
    void updatePlato() throws RepositorioException, EntidadNoEncontrada {
        Plato plato = new Plato();
        plato.setNombre("Test Plato");
        System.out.println("id updplato:" + restaurante.getId());
        servicioRestaurantes.addPlato(restaurante.getId(), plato);
        plato.setDescripcion("Nueva Descripcion");
        servicioRestaurantes.updatePlato(restaurante.getId(), plato);
    }

    @Test
    void removeRestaurante() throws RepositorioException, EntidadNoEncontrada {
        servicioRestaurantes.removeRestaurante(restaurante.getId());
        assertThrows(EntidadNoEncontrada.class, () -> servicioRestaurantes.getRestaurante(restaurante.getId()));
    }

    @Test
    void getListadoRestaurantes() throws RepositorioException {
        List<RestauranteResumen> listado = servicioRestaurantes.getListadoRestaurantes();
        assertTrue(listado.size() > 0);
    }
}

