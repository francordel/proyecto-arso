package restaurantes.modelo;

public class EventoNuevaValoracion {
    private String idOpinion;
    private Valoracion nuevaValoracion;
    private int numeroValoraciones;
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
}
