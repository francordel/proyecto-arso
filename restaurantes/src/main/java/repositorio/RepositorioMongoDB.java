package repositorio;

import java.util.List;

public abstract class RepositorioMongoDB<T extends Identificable> implements RepositorioString<T>{
	MongoCollection<T> mongoCollection;
	public RepositorioMongoDB(){
		//LO FINO
		//mongoCollection = generico();
		//lO BRUTO
		mongoCollection = obtenerColeccion();
	}
	/*ABILES
	public MongoCollection<T> generico(){
		/**************************************MONGODB****************************************/
        
    	ConnectionString connectionString = new ConnectionString("mongodb+srv://misintaxis:misintaxis@franxpablo.eraamtt.mongodb.net/?retryWrites=true&w=majority");
		//I need to configure the CodecRegistry to include a codec to handle the translation to and from BSON for our POJOs.
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		//And I need to add the default codec registry, which contains all the default codecs. They can handle all the major types in Java-like Boolean, Double, String, BigDecimal, etc.
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

		MongoClient mongoClient = MongoClients.create(settings);

		MongoDatabase database = mongoClient.getDatabase("test");


		System.out.println(database.getName());

		MongoCollection<T> mongoCollection = obtenerColeccion();
		return mongoCollection;
	}
	
	//		MongoCollection<Restaurante> restaurantes = database.getCollection("restaurantes", Restaurante.class);

	//A LO BRUTO QUE LA CLASE HIJA IMPLEMENTE TODO LO DE OBTENER LA COLECCION
	public MongoCollection<T> obtenerColeccion();

	@Override
	public String add(T entity) throws RepositorioException {
		mongoCollection.insertOne(entity);
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
