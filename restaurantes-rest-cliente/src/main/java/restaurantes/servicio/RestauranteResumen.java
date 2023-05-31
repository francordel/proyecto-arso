package restaurantes.servicio;

public class RestauranteResumen {

	private String id;
	private String nombre;
	private String coordenadas;
	private String codigoPostal;
	private String ciudad;
	private double calificacionMedia;
	
	
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public double getCalificacionMedia() {
		return calificacionMedia;
	}

	public void setCalificacionMedia(double calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
	
	@Override
	public String toString() {
		return "RestauranteResumen [id=" + id + ", nombre=" + nombre + ", coordenadas=" + coordenadas
				+ ", codigoPostal=" + codigoPostal + "]";
	}
	
}
