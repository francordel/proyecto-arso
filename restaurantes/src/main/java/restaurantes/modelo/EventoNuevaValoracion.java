package restaurantes.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventoNuevaValoracion {

    @JsonProperty("IdOpinion")           
    private String idOpinion;
    @JsonProperty("NuevaValoracion")
    private Valoracion nuevaValoracion;
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
	public Valoracion getNuevaValoracion() {
		return nuevaValoracion;
	}
	public void setNuevaValoracion(Valoracion nuevaValoracion) {
		this.nuevaValoracion = nuevaValoracion;
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
		return "EventoNuevaValoracion [idOpinion=" + idOpinion + ", nuevaValoracion=" + nuevaValoracion
				+ ", numeroValoraciones=" + numeroValoraciones + ", calificacionMedia=" + calificacionMedia + "]";
	}
	
}
