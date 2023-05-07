package bookle.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import bookle.servicio.ActividadResumen;

@XmlRootElement
public class Listado {

	public static class ResumenExtendido {
		
		private String url;
		private ActividadResumen resumen;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public ActividadResumen getResumen() {
			return resumen;
		}
		public void setResumen(ActividadResumen resumen) {
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
	
	
	private List<ResumenExtendido> actividad;
	
	public List<ResumenExtendido> getActividad() {
		return actividad;
	}
	
	public void setActividad(List<ResumenExtendido> actividad) {
		this.actividad = actividad;
	}
}
