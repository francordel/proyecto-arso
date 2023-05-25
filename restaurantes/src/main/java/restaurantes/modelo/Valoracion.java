package restaurantes.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Valoracion {
	@JsonProperty("CorreoElectronico")
	private String correoElectronico;
	@JsonProperty("FechaRegistro")
    private String fechaRegistro;
	@JsonProperty("Calificacion")
    private int calificacion;
	@JsonProperty("Comentario")
    private String comentario;
      
    public Valoracion() {
		// TODO Auto-generated constructor stub
	}
    
	public Valoracion(String correoElectronico, LocalDate fechaRegistro, int calificacion, String comentario) {
		this.correoElectronico = correoElectronico;
        this.fechaRegistro = fechaRegistro.format(DateTimeFormatter.ISO_DATE); // Convertir LocalDate a String	
        this.calificacion = calificacion;
		this.comentario = comentario;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
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
