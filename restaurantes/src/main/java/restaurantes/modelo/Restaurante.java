package restaurantes.modelo;

import java.util.LinkedList;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorio.Identificable;

public class Restaurante implements Identificable{
	@BsonId()
    @BsonRepresentation(BsonType.STRING)
    private String id;
    private String nombre;
    private String coordenadas;
    private String codigoPostal;
    private List<Plato> platos = new LinkedList<Plato>();
    private List<SitioTuristico> sitios = new LinkedList<SitioTuristico>();

    /*
     * UNION CON OPINIONES
     */

    private int numValoraciones;
    private double calificacionMedia;
    private String idOpinion;
    
    public int getNumValoraciones() {
		return numValoraciones;
	}

	public void setNumValoraciones(int numValoraciones) {
		this.numValoraciones = numValoraciones;
	}

	public double getCalificacionMedia() {
		return calificacionMedia;
	}

	public void setCalificacionMedia(double calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}

	public String getIdOpinion() {
		return idOpinion;
	}

	public void setIdOpinion(String idOpinion) {
		this.idOpinion = idOpinion;
	}

	public String  getId() {
        return id;
    }

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
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
