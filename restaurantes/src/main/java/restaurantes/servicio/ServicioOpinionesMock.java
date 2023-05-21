package restaurantes.servicio;

import java.util.*;

import restaurantes.modelo.Opinion;
import restaurantes.modelo.Valoracion;

public class ServicioOpinionesMock implements IServicioOpiniones {
    
    private Map<String, Opinion> opiniones;
    private int idCounter;

    public ServicioOpinionesMock() {
        this.opiniones = new HashMap<>();
        this.idCounter = 0;
    }

    @Override
    public void crearOpinion(Opinion opinion) {
        String idOpinion = generateId();
        opinion.setId(idOpinion);
        this.opiniones.put(idOpinion, opinion);
    }

    @Override
    public List<Valoracion> recuperarValoraciones(String identificadorOpinion) {
        Opinion opinion = this.opiniones.get(identificadorOpinion);
        if(opinion != null) {
            return opinion.getValoraciones();
        }
        return null;
    }

    private String generateId() {
        return Integer.toString(this.idCounter++);
    }
}
