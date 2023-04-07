package restaurantes.repositorio;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import repositorio.RepositorioMongoDB;
import restaurantes.modelo.Restaurante;

public class RepositorioRestauranteMongoDB extends RepositorioMongoDB<Restaurante> {

    public RepositorioRestauranteMongoDB() {
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

