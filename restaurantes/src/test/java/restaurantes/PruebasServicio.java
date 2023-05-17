package restaurantes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;

import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.servicio.RestauranteResumen;
import restaurantes.servicio.ServicioRestaurantes;

class ServicioRestaurantesTest {
    private ServicioRestaurantes servicioRestaurantes;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() throws RepositorioException, EntidadNoEncontrada {
        servicioRestaurantes = new ServicioRestaurantes();
        String id = servicioRestaurantes.create("Test Restaurante", "12345", "1.2345,1.2345");
        restaurante = servicioRestaurantes.getRestaurante(id);
    }

    @Test
    void create() {
        assertNotNull(restaurante);
        assertEquals("Test Restaurante", restaurante.getNombre());
    }

    @Test
    void update() throws RepositorioException, EntidadNoEncontrada {
        servicioRestaurantes.update(restaurante.getId(), "Nuevo Nombre", "54321", "5.4321,5.4321");
        Restaurante restauranteActualizado = servicioRestaurantes.getRestaurante(restaurante.getId());
        assertEquals("Nuevo Nombre", restauranteActualizado.getNombre());
    }

    @Test
    void addPlato() throws RepositorioException, EntidadNoEncontrada {
        Plato plato = new Plato();
        plato.setNombre("Test Plato");
        servicioRestaurantes.addPlato(restaurante.getId(), plato);
        assertTrue(restaurante.getPlatos().contains(plato));
    }

    @Test
    void removePlato() throws RepositorioException, EntidadNoEncontrada {
        Plato plato = new Plato();
        plato.setNombre("Test Plato");
        servicioRestaurantes.addPlato(restaurante.getId(), plato);
        servicioRestaurantes.removePlato(restaurante.getId(), plato.getNombre());
        assertFalse(restaurante.getPlatos().contains(plato));
    }

    @Test
    void updatePlato() throws RepositorioException, EntidadNoEncontrada {
        Plato plato = new Plato();
        plato.setNombre("Test Plato");
        servicioRestaurantes.addPlato(restaurante.getId(), plato);
        plato.setDescripcion("Nueva Descripcion");
        servicioRestaurantes.updatePlato(restaurante.getId(), plato);
        assertEquals("Nueva Descripcion", restaurante.getPlatos().get(0).getDescripcion());
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

