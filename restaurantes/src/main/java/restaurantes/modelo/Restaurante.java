package restaurantes.modelo;

import java.util.LinkedList;
import java.util.List;

public class Restaurante {
    private String id;
    private String nombre;
    private String coordenadas;
    private List<SitioTuristico> sitio = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public List<SitioTuristico> getSitio() {
        return sitio;
    }

    public void setSitio(List<SitioTuristico> sitio) {
        this.sitio = sitio;
    }
    
	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nombre=" + nombre + ", coordenadas=" + coordenadas + ", sitios turisticos=" + sitio + "]";
	}
}
