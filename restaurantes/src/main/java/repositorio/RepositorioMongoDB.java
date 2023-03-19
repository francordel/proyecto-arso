package repositorio;

import java.util.List;

public class RepositorioMongoDB<T extends Identificable> implements RepositorioString<T>{

	@Override
	public String add(T entity) throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

}
