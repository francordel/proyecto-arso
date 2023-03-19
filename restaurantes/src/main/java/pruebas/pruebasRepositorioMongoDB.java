package pruebas;

import factoriaServicio.FactoriaServicios;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import restaurantes.servicio.IServicioRestaurantes;

public class pruebasRepositorioMongoDB {
	public static String getUrlByPostalCode(String postalCode) {
        String url = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + postalCode
                + "&country=ES&radius=10&username=arso&lang=es";
        return url;
    }
	public static void main(String[] args) {
		//Obtengo el servicio de Restaurantes
		IServicioRestaurantes servicio = FactoriaServicios.getServicio(IServicioRestaurantes.class);
		
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
		try {
			String id = servicio.create(restaurante);
			System.out.println("Restaurante Id asignado: " + id);
			Restaurante r1 = servicio.getRestaurante(id);
			System.out.println(r1);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		System.out.println("Fin.");

	}

}
