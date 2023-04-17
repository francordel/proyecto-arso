package restaurantes.rest;

import java.time.LocalDateTime;
import java.util.List;

import restaurantes.servicio.RestauranteResumen;

public class Listado {

	public static class ResumenExtendido {
		
		private String url;
		private RestauranteResumen resumen;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public RestauranteResumen getResumen() {
			return resumen;
		}
		public void setResumen(RestauranteResumen resumen) {
			this.resumen = resumen;
		}
		
	}
	
	private LocalDateTime fecha = LocalDateTime.now();
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	
	private List<ResumenExtendido> restaurante;
	
	public List<ResumenExtendido> getRestaurante() {
		return restaurante;
	}
	
	public void setRestaurante(List<ResumenExtendido> restaurante) {
		this.restaurante = restaurante;
	}
}
