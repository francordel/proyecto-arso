package opiniones.modelo;

import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

import java.util.LinkedList;
public class SitioTuristico {
	@BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;
	private String titulo;
    private String resumen;
    private List<String> categorias = new LinkedList<String>();
    private List<String> enlacesExternos = new LinkedList<String>();
    private List<String> imagen = new LinkedList<String>();

    public String  getId() {
        return id;
    }

	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
 
    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public List<String> getEnlacesExternos() {
        return enlacesExternos;
    }

    public void setEnlacesExternos(List<String> enlacesExternos) {
        this.enlacesExternos = enlacesExternos;
    }
    
    public List<String> getImagenes() {
        return imagen;
    }
    
    public void setImagenes(List<String> imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "SitioTuristico [titulo=" + titulo + ", resumen=" + resumen + ", categorias=" + categorias + ", enlaces externos=" + enlacesExternos + ", imagen=" + imagen + "]";
    }

}
