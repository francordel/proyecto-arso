package pruebas;

import repositorio.RepositorioException;
import repositorio.RepositorioRestauranteMongoDB;
import restaurantes.modelo.Restaurante;

public class pruebaRepoRestrauranteMongoDB {
	public static void main(String[] args) {
		RepositorioRestauranteMongoDB repo = new RepositorioRestauranteMongoDB();
		System.out.println("hola");
		//Insertamos un restaurante
		Restaurante MasKTapas = new Restaurante();
		MasKTapas.setNombre("Mas Ke Tapas American Grill");
		MasKTapas.setCodigoPostal("30011");
		MasKTapas.setCoordenadas("37.97692765766116, -1.1244507925484326");
		try {
			repo.add(MasKTapas);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
