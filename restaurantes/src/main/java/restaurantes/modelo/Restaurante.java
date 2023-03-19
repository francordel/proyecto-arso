package restaurantes.modelo;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Restaurante {

    private ObjectId  id;
    private String nombre;
    private String coordenadas;
    private String codigoPostal;
    private List<Plato> platos = new LinkedList<Plato>();
    private List<SitioTuristico> sitios = new LinkedList<SitioTuristico>();

    public ObjectId getId() {
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
    
    public List<Plato> getPlatos() {
		return platos;
	}
    
    public void setPlatos(List<Plato> platos) {
		this.platos = platos;
	}

	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nombre=" + nombre + ", codigo postal="+codigoPostal+", coordenadas=" + coordenadas + ", sitios=" + sitios + "]";
	}
}
