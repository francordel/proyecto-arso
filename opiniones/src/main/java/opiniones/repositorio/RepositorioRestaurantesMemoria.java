package opiniones.repositorio;

import opiniones.modelo.Restaurante;
import opiniones.modelo.SitioTuristico;
import repositorio.RepositorioException;
import repositorio.RepositorioMemoria;

public class RepositorioRestaurantesMemoria extends RepositorioMemoria<Restaurante>{
	// Datos iniciales
	public RepositorioRestaurantesMemoria()  {
		try {
			Restaurante restaurante = new Restaurante();
	
			restaurante.setNombre("El restaurante de la esquina");
	
	
			for(int i=0; i<3; i++) {
				SitioTuristico sitio = new SitioTuristico();
				sitio.setTitulo("Altorreal"+i);
				sitio.setResumen("Morada del rey Pablo X el Sabio");
				sitio.getImagenes().add("https://www.elperiodico.com/es/imagenes/2019/05/14/actualidad/1557840000_000000_1557840000_noticia_normal.jpg");
				for(int j = 0; j<3; j++) {
					sitio.getEnlacesExternos().add("https://www.misintaxis.com"+j);
				}
				System.out.println("Sitio Id asignado: " + sitio.getId());
	
				restaurante.getSitios().add(sitio);
			}
			this.add(restaurante);
	
			System.out.println("Fin.");
			
		} catch (RepositorioException e) {
			e.printStackTrace(); // no debe suceder en un repositorio en memoria
		}
	}
}
   
