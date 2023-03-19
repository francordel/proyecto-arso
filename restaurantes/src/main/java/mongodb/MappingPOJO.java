package mongodb;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;

public class Programa {
	public static void main(String[] args) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://misintaxis:misintaxis@franxpablo.eraamtt.mongodb.net/?retryWrites=true&w=majority");
		
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

		MongoClient mongoClient = MongoClients.create(settings);

		MongoDatabase database = mongoClient.getDatabase("test");


		System.out.println(database.getName());

		MongoCollection<Restaurante> restaurantes = database.getCollection("restaurantes", Restaurante.class);
		MongoCollection<SitioTuristico> sitiosTuristicos = database.getCollection("sitiosturisticos", SitioTuristico.class);

		Restaurante restaurante = new Restaurante();

		restaurante.setNombre("El restaurante de la esquina");


		for(int i=0; i<3; i++) {
			SitioTuristico sitio = new SitioTuristico();
			sitio.setTitulo("Altorreal"+i);
			sitio.setResumen("Morada del rey Pablo X el Sabio");
			sitio.setImagen("https://www.elperiodico.com/es/imagenes/2019/05/14/actualidad/1557840000_000000_1557840000_noticia_normal.jpg");
			for(int j = 0; j<3; j++) {
				sitio.getEnlacesExternos().add("https://www.misintaxis.com"+j);
			}
			sitiosTuristicos.insertOne(sitio);
			System.out.println("Sitio Id asignado: " + sitio.getId());

			restaurante.getSitios().add(sitio);
		}

		restaurantes.insertOne(restaurante);
		System.out.println("Restaurante Id asignado: " + restaurante.getId());

		for (Restaurante restaurante1: restaurantes.find()) {
            	
			System.out.println(restaurante1);
		}
		System.out.println("Fin.");

	}
}
