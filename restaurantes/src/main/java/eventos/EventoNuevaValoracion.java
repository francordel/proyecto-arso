package eventos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoNuevaValoracion {
	
    @JsonProperty("IdOpinion")           
    private String idOpinion;
    @JsonProperty("NumeroValoraciones")
    private int numeroValoraciones;
    @JsonProperty("CalificacionMedia")
    private double calificacionMedia;
    
	public String getIdOpinion() {
		return idOpinion;
	}
	public void setIdOpinion(String idOpinion) {
		this.idOpinion = idOpinion;
	}

	public int getNumeroValoraciones() {
		return numeroValoraciones;
	}
	public void setNumeroValoraciones(int numeroValoraciones) {
		this.numeroValoraciones = numeroValoraciones;
	}
	public double getCalificacionMedia() {
		return calificacionMedia;
	}
	public void setCalificacionMedia(double calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}
	@Override
	public String toString() {
		return "EventoNuevaValoracion [idOpinion=" + idOpinion 
				+ ", numeroValoraciones=" + numeroValoraciones + ", calificacionMedia=" + calificacionMedia + "]";
	}
	
}
