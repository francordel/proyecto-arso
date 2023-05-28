package restaurantes.retrofit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import restaurantes.modelo.Valoracion;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValoracionesResponse {
	
    private List<Valoracion> valoraciones;

	public List<Valoracion> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}
}
