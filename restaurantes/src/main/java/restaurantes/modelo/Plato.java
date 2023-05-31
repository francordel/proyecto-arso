package restaurantes.modelo;

import java.util.Objects;

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
	
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Plato plato = (Plato) obj;
	    return Objects.equals(nombre, plato.nombre);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(nombre);
	}


}
