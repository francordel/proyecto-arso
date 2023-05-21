package restaurantes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import restaurantes.modelo.Opinion;
import restaurantes.modelo.Valoracion;
import restaurantes.servicio.ServicioOpinionesMock;

import java.util.List;
import java.util.LinkedList;

public class PruebasServicioOpiniones {

    private ServicioOpinionesMock servicio;
    private Opinion opinion;

    @BeforeEach
    public void setup() {
        servicio = new ServicioOpinionesMock();
        opinion = new Opinion();
        opinion.setNombreRecurso("Test Opinion");
    }

    @Test
    public void crearOpinionTest() {
        servicio.crearOpinion(opinion);
        assertNotNull(opinion.getId(), "La id de la opinion no puede ser null");
    }

    @Test
    public void recuperarValoracionesTest() {
        servicio.crearOpinion(opinion);
        Valoracion valoracion = new Valoracion();
        valoracion.setCalificacion(5);
        valoracion.setComentario("Great!");
        opinion.setValoraciones(new LinkedList<Valoracion>());
        opinion.getValoraciones().add(valoracion);
        
        List<Valoracion> valoraciones = servicio.recuperarValoraciones(opinion.getId());
        
        assertNotNull(valoraciones, "Valoraciones no deberia ser null");
        assertEquals(1, valoraciones.size(), "Deberia existir al menos una valoracion");
        assertEquals(valoracion.getCalificacion(), valoraciones.get(0).getCalificacion());
        assertEquals(valoracion.getComentario(), valoraciones.get(0).getComentario());
    }

}
