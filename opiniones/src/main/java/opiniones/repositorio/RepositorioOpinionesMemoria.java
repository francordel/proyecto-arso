package opiniones.repositorio;

import java.util.LinkedList;
import java.util.List;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import repositorio.RepositorioException;
import repositorio.RepositorioMemoria;

public class RepositorioOpinionesMemoria extends RepositorioMemoria<Opinion>{
	// Datos iniciales
	public RepositorioOpinionesMemoria()  {
		try {
			List<Valoracion> valoraciones = new LinkedList<Valoracion>();
			valoraciones.add(new Valoracion("fran.cortes@gmail.com", 5, "Maravilloso Instagram"));
			valoraciones.add(new Valoracion("gonzalo.canovas@gmail.com", 4));
			Opinion opinion = new Opinion("instagram @misintaxis", valoraciones);
			this.add(opinion); 
		} catch (RepositorioException e) {
			e.printStackTrace(); // no debe suceder en un repositorio en memoria
		}
	}
}
   
