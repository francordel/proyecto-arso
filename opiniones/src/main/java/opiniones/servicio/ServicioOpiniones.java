package opiniones.servicio;

import java.util.LinkedList;
import java.util.List;

import opiniones.modelo.Plato;
import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioOpiniones implements IServicioOpiniones {
	
	private Repositorio<Opinion, String> repositorio = FactoriaRepositorios.getRepositorio(Opinion.class);

	@Override
	public String create(String nombre, String codigoPostal, String coordenadas) throws RepositorioException {
		Opinion restaurante = new Opinion();
		restaurante.setNombre(nombre);
		restaurante.setCodigoPostal(codigoPostal);
		restaurante.setCoordenadas(coordenadas);
		
		return repositorio.add(restaurante);
	}

	@Override
	public void update(String id, String nombre, String codigoPostal, String coordenadas)
			throws RepositorioException, EntidadNoEncontrada {
		
		// DUDA CON PUT: ¿QUÉ PASA SI QUIERES CAMBIAR SOLO UNO DE LOS CAMPOS Y QUEDAN CAMPOS VACÍOS?
		// ¿QUÉ LE LLEGA AL PUT? CADENA VACÍA, NULL...?

		Opinion restaurante = repositorio.getById(id);
		
		restaurante.setNombre(nombre);
		restaurante.setCodigoPostal(codigoPostal);
		restaurante.setCoordenadas(coordenadas);

		repositorio.update(restaurante);
	}

	@Override
	public List<Valoracion> getSitiosProximos(String id) throws RepositorioException, EntidadNoEncontrada {
		Opinion restaurante = repositorio.getById(id);

		return restaurante.getSitios();
	}

	@Override
	public void setSitiosDestacados(String id, List<Valoracion> sitios)
			throws RepositorioException, EntidadNoEncontrada {
		
		Opinion restaurante = repositorio.getById(id);
		
		restaurante.setSitios(sitios);
	}

	@Override
	public void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {
		Opinion restaurante = repositorio.getById(id);
		
		List<Plato> platos = restaurante.getPlatos();
		
		for (Plato platoExistente : platos) {
			if (platoExistente.getNombre().equals(plato.getNombre())) {
				throw new IllegalArgumentException("Ya existe un plato con este nombre");
			}
		}

		platos.add(plato);
		restaurante.setPlatos(platos);
		
	}

	@Override
	public void removePlato(String id, String nombre) throws RepositorioException, EntidadNoEncontrada {
		
		Opinion restaurante = repositorio.getById(id);
		
		List<Plato> platos = restaurante.getPlatos();
		
		for (Plato plato : platos) {
			if (plato.getNombre().equals(nombre)) {
				platos.remove(plato);
				restaurante.setPlatos(platos);
			}
		}
	}

	@Override
	public void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {
		Opinion restaurante = repositorio.getById(id);
		
		List<Plato> platos = restaurante.getPlatos();
		
		for (Plato platoExistente : platos) {
			if (platoExistente.getNombre().equals(plato.getNombre())) {
				platos.remove(platoExistente);
				platos.add(plato);
				restaurante.setPlatos(platos);
			}
		}
	}

	@Override
	public Opinion getRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {
		return repositorio.getById(id);
	}

	@Override
	public void removeRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {
		Opinion restaurante = repositorio.getById(id);
		
		repositorio.delete(restaurante);
	}

	@Override
	public List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException {
		
		LinkedList<RestauranteResumen> resultado = new LinkedList<>();

		
		for (String id : repositorio.getIds()) {
			try {
				Opinion restaurante = getRestaurante(id);
				RestauranteResumen resumen = new RestauranteResumen();
				resumen.setId(restaurante.getId());
				resumen.setNombre(restaurante.getNombre());
				resumen.setCoordenadas(restaurante.getCoordenadas());
				resumen.setCodigoPostal(restaurante.getCodigoPostal());
				
				resultado.add(resumen);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}

}
