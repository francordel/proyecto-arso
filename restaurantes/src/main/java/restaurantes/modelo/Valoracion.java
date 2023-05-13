package restaurantes.modelo;

import java.time.LocalDate;

public class Valoracion {
	private String correoElectronico;
    private LocalDate fechaRegistro;
    private int calificacion;
    private String comentario;
    
    
	public Valoracion(String correoElectronico, LocalDate fechaRegistro, int calificacion, String comentario) {
		this.correoElectronico = correoElectronico;
		this.fechaRegistro = fechaRegistro;
		this.calificacion = calificacion;
		this.comentario = comentario;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
    
}
