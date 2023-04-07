package opiniones.modelo;

public class Plato {
	private String nombre;
	private String descripcion;
	private int precio;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getPrecio() {
		return precio;
	}
	
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Plato [nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + "]";
	}
	

}
