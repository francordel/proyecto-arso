package restaurantes.retrofit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import restaurantes.modelo.Valoracion;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpinionResponse {

    private String id;
    private String nombreRecurso;
    private List<Valoracion> valoraciones;
    private double valoracionMedia;
    private int numeroValoraciones;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombreRecurso() {
		return nombreRecurso;
	}
	public void setNombreRecurso(String nombreRecurso) {
		this.nombreRecurso = nombreRecurso;
	}
	public List<Valoracion> getValoraciones() {
		return valoraciones;
	}
	public void setValoraciones(List<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}
	public double getValoracionMedia() {
		return valoracionMedia;
	}
	public void setValoracionMedia(double valoracionMedia) {
		this.valoracionMedia = valoracionMedia;
	}
	public int getNumeroValoraciones() {
		return numeroValoraciones;
	}
	public void setNumeroValoraciones(int numeroValoraciones) {
		this.numeroValoraciones = numeroValoraciones;
	}

    
    
}

