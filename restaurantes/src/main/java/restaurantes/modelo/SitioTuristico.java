package restaurantes.modelo;

import java.util.List;

public class SitioTuristico {
    private String titulo;
    private String resumen;
    private List<String> categorias;
    private List<String> enlacesExternos;
    private String imagen;

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
    
    public String getImagen() {
        return imagen;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "SitioTuristico [titulo=" + titulo + ", resumen=" + resumen + ", categorias=" + categorias + ", enlaces externos=" + enlacesExternos + ", imagen=" + imagen + "]";
    }

}
