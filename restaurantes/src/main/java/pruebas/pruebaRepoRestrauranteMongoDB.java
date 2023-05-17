package pruebas;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import restaurantes.modelo.Restaurante;
import restaurantes.repositorio.RepositorioRestauranteMongoDB;

public class pruebaRepoRestrauranteMongoDB {
	public static void main(String[] args) {
		RepositorioRestauranteMongoDB repo = new RepositorioRestauranteMongoDB();
		System.out.println("hola");
		//Insertamos un restaurante
		Restaurante MasKTapas = new Restaurante();
		MasKTapas.setNombre("Mas Ke Tapas American Grill");
		MasKTapas.setCodigoPostal("30011");
		MasKTapas.setCoordenadas("37.97692765766116, -1.1244507925484326");
		String id="";
		try {
			id += repo.add(MasKTapas);
			System.out.println("id="+id);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Restaurante rest = repo.getById(id);
			System.out.println(rest);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
