package restaurantes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import restaurantes.modelo.Valoracion;
import restaurantes.servicio.ServicioOpinionesMock;

import java.util.List;

public class PruebasServicioOpiniones {

    private ServicioOpinionesMock servicio;
    private String nombreRestaurante;
    private String idOpinion;

    @BeforeEach
    public void setup() {
        servicio = new ServicioOpinionesMock();
        nombreRestaurante = "Test Restaurant";
    }

    @Test
    public void crearOpinionTest() {
        idOpinion = servicio.crearOpinion(nombreRestaurante);
        assertNotNull(idOpinion, "La id de la opinion no puede ser null");
    }

    @Test
    public void recuperarValoracionesTest() {
        idOpinion = servicio.crearOpinion(nombreRestaurante);
        Valoracion valoracion = new Valoracion();
        valoracion.setCalificacion(5);
        valoracion.setComentario("Buen restaurante");
        List<Valoracion> valoraciones = servicio.recuperarValoraciones(idOpinion);
        valoraciones.add(valoracion);
        
        List<Valoracion> retrievedValoraciones = servicio.recuperarValoraciones(idOpinion);
        
        assertNotNull(retrievedValoraciones, "Valoraciones no deberia ser null");
        assertEquals(1, retrievedValoraciones.size(), "Deberia existir al menos una valoracion");
        assertEquals(valoracion.getCalificacion(), retrievedValoraciones.get(0).getCalificacion());
        assertEquals(valoracion.getComentario(), retrievedValoraciones.get(0).getComentario());
    }
}
