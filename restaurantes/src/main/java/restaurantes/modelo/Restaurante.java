package restaurantes.modelo;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Restaurante {
    @BsonProperty("_id")
    private String id;
    private String nombre;
    private String coordenadas;
    private String codigoPostal;
    private List<SitioTuristico> sitios = new LinkedList<SitioTuristico>();

    public String getId() {
        return id;
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
        
    
    public String getCodigoPostal() {
		return codigoPostal;
	}


	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}


	public List<SitioTuristico> getSitios() {
        return sitios;
    }

    public void setSitios(List<SitioTuristico> sitios) {
        this.sitios = sitios;
    }

	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nombre=" + nombre + ", coordenadas=" + coordenadas + ", sitios=" + sitios + "]";
	}
}
