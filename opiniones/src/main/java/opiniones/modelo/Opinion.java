package opiniones.modelo;

import java.util.LinkedList;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorio.Identificable;

public class Opinion implements Identificable {
	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private String nombreRecurso;

	private List<Valoracion> valoraciones = new LinkedList<Valoracion>();

	public Opinion(String nombreRecurso, List<Valoracion> valoraciones) {

		this.nombreRecurso = nombreRecurso;
		this.valoraciones = valoraciones;

	}

	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public String getNombreRecurso() {
		return nombreRecurso;
	}

	public void setNombreRecurso(String nombreRecurso) {
		this.nombreRecurso = nombreRecurso;
	}

	public double getValoracionMedia() {
		double sum = 0.0;
		for (Valoracion v : valoraciones) {
			sum+=v.getCalificacion();
		}
		return sum/valoraciones.size();
	}

	public int getNumeroValoraciones() {
		return valoraciones.size();
	}

	public List<Valoracion> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}

	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nombreRecurso=" + nombreRecurso + ", codigo postal=" + getValoraciones()
				+ ", coordenadas=" + getValoracionMedia() + ", valoraciones=" + valoraciones + "]";
	}

}
