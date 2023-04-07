package opiniones.repositorio;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import opiniones.modelo.Opinion;
import repositorio.RepositorioMongoDB;

public class RepositorioOpinionesMongoDB extends RepositorioMongoDB<Opinion> {

    public RepositorioOpinionesMongoDB() {
    	// Llamar al constructor de la clase padre
    	super(); 
    }

    @Override
    public MongoCollection<Opinion> obtenerColeccion(MongoDatabase database) {
    	// Obtener la colección de restaurantes
        MongoCollection<Opinion> collection = database.getCollection("opiniones", Opinion.class); 
        return collection;
    }

}

