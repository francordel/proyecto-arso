package repositorio;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import opiniones.modelo.Restaurante;

public class RepositorioOpinionesMongoDB extends RepositorioMongoDB<Restaurante> {

    public RepositorioOpinionesMongoDB() {
    	// Llamar al constructor de la clase padre
    	super(); 
    }

    @Override
    public MongoCollection<Restaurante> obtenerColeccion(MongoDatabase database) {
    	// Obtener la colección de restaurantes
        MongoCollection<Restaurante> collection = database.getCollection("restaurantes", Restaurante.class); 
        return collection;
    }

}

