package usuarios.repositorio;

import repositorio.RepositorioException;
import repositorio.RepositorioMemoria;
import usuarios.modelo.Rol;
import usuarios.modelo.Usuario;

public class RepositorioUsuariosMemoria extends RepositorioMemoria<Usuario> {

	public RepositorioUsuariosMemoria()  {
		
		// Datos iniciales
		
		try {
			Usuario usuario1 = new Usuario("Marcos", "marcos@um.es", "MarcosMenarguez", Rol.PROFESOR);
			this.add(usuario1);
			
			Usuario usuario2 = new Usuario("Juan", "juan@um.es", "juanito", Rol.ALUMNO);
			this.add(usuario2);
			
		} catch (RepositorioException e) {
			e.printStackTrace(); // no debe suceder en un repositorio en memoria
		}
		
	}
	
}
