package opiniones.modelo;


import java.time.LocalDate;

public class Valoracion {

	private String correoElectronico;
    private LocalDate fechaRegistro;
    private int calificacion;
    private String comentario;
    
	public Valoracion(String correoElectronico, int calificacion, String comentario) {
		this.correoElectronico = correoElectronico;
		this.fechaRegistro = LocalDate.now();
        if (calificacion >= 1 && calificacion <= 5) {
            this.calificacion = calificacion;
        } else {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
		this.comentario = comentario;
	}
	public Valoracion(String correoElectronico, int calificacion) {
		this(correoElectronico, calificacion, "");
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
        if (calificacion >= 1 && calificacion <= 5) {
            this.calificacion = calificacion;
        } else {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
    }
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	@Override
	public String toString() {
		return "Valoracion [correoElectronico=" + correoElectronico + ", fechaRegistro=" + fechaRegistro
				+ ", calificacion=" + calificacion + ", comentario=" + comentario + "]";
	}
    

}
