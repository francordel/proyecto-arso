package restaurantes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.servicio.RestauranteResumen;
import restaurantes.servicio.ServicioRestaurantes;

class ServicioRestaurantesTest {
    private ServicioRestaurantes servicioRestaurantes;
    private Restaurante restaurante;
    private Plato plato;
    private final String platoNombre = "Test Plato";

    @BeforeEach
    void setUp() throws RepositorioException, EntidadNoEncontrada {
        servicioRestaurantes = new ServicioRestaurantes();
        String id = servicioRestaurantes.create("Test Restaurante", "30007", "1,1", "", "Murcia");
        restaurante = servicioRestaurantes.getRestaurante(id);

        plato = new Plato();
        plato.setNombre(platoNombre);
        servicioRestaurantes.addPlato(restaurante.getId(), plato);
    }

    @AfterEach
    void tearDown() {
        try {
            servicioRestaurantes.removeRestaurante(restaurante.getId());
        } catch (EntidadNoEncontrada | RepositorioException e) {
            e.printStackTrace();
        }
    }

    // Existing test methods with some improvements...
    @Test
    void getRestaurante() throws RepositorioException, EntidadNoEncontrada {
        Restaurante foundRestaurante = servicioRestaurantes.getRestaurante(restaurante.getId());
        assertNotNull(foundRestaurante);
        assertEquals(restaurante.getNombre(), foundRestaurante.getNombre());
    }

    @Test
    void create() {
        assertNotNull(restaurante);
        assertEquals("Test Restaurante", restaurante.getNombre());
    }

    @Test
    void update() throws RepositorioException, EntidadNoEncontrada {
        servicioRestaurantes.update(restaurante.getId(), "Nuevo Nombre", "54321", "5,5", "Alicante");
        Restaurante restauranteActualizado = servicioRestaurantes.getRestaurante(restaurante.getId());
        assertEquals("Nuevo Nombre", restauranteActualizado.getNombre());
    }

    @Test
    void addPlato() throws RepositorioException, EntidadNoEncontrada {
        Plato newPlato = new Plato();
        newPlato.setNombre("Nuevo Plato");
        servicioRestaurantes.addPlato(restaurante.getId(), newPlato);
        assertTrue(servicioRestaurantes.getRestaurante(restaurante.getId()).getPlatos().contains(newPlato));
    }

    @Test
    void removePlato() throws RepositorioException, EntidadNoEncontrada {
        servicioRestaurantes.removePlato(restaurante.getId(), plato.getNombre());
        assertFalse(servicioRestaurantes.getRestaurante(restaurante.getId()).getPlatos().contains(plato));
    }

    @Test
    void updatePlato() throws RepositorioException, EntidadNoEncontrada {
        plato.setDescripcion("Nueva Descripcion");
        servicioRestaurantes.updatePlato(restaurante.getId(), plato);
        assertTrue(servicioRestaurantes.getRestaurante(restaurante.getId()).getPlatos().stream().anyMatch(p -> p.getDescripcion().equals("Nueva Descripcion")));
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
    
    @Test
    void getRestauranteNotFound() {
        assertThrows(EntidadNoEncontrada.class, () -> servicioRestaurantes.getRestaurante("id_inexistente"));
    }

    @Test
    void removeRestauranteNotFound() {
        assertThrows(EntidadNoEncontrada.class, () -> servicioRestaurantes.removeRestaurante("id_inexistente"));
    }

    @Test
    void addPlatoToNonExistentRestaurante() {
        Plato newPlato = new Plato();
        newPlato.setNombre("Nuevo Plato");
        assertThrows(EntidadNoEncontrada.class, () -> servicioRestaurantes.addPlato("id_inexistente", newPlato));
    }

    @Test
    void removePlatoFromNonExistentRestaurante() {
        assertThrows(EntidadNoEncontrada.class, () -> servicioRestaurantes.removePlato("id_inexistente", "Nombre Plato"));
    }

}
