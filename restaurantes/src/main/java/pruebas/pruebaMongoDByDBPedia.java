package pruebas;

import java.io.InputStreamReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;




import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;

public class pruebaMongoDByDBPedia {
	public static String getUrlByPostalCode(String postalCode) {
        String url = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + postalCode
                + "&country=ES&radius=10&username=arso&lang=es";
        return url;
    }

    public static void main(String[] args) throws Exception {
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

		MongoCollection<Restaurante> restaurantes = database.getCollection("restaurantes", Restaurante.class);
		MongoCollection<SitioTuristico> sitiosTuristicos = database.getCollection("sitiosturisticos", SitioTuristico.class);
/*	
		//Insertamos un restaurante
 		Restaurante laPeque = new Restaurante();
		laPeque.setNombre("La pequeña taberna");
		laPeque.setCodigoPostal("30003");
		laPeque.setCoordenadas("37,982494, -1,126312");
		restaurantes.insertOne(laPeque);
		 
*/


		for(Restaurante restaurante : restaurantes.find()) {
			System.out.println("Restaurante id " + restaurante.getId());
			System.out.println("Restaurante nombre: " + restaurante.getNombre());
			String codigoPostal = restaurante.getCodigoPostal();
			System.out.println("Restaurante Codigo Postal " + codigoPostal);
			/*****************************DBPEDIA***************/
			System.out.println("VAMOS A OBTENER LOS SITIOS TURISTICOS:");
	        // 1. Obtener una factoría
	        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

	        // 2. Pedir a la factoría la construcción del analizador
	        DocumentBuilder analizador = factoria.newDocumentBuilder();
	        String url;
	        if(codigoPostal!=null)
	        	 url = getUrlByPostalCode(codigoPostal);
	        else
	        	url = "30040";

	        Document documento = analizador.parse(url);

	        NodeList elementos = documento.getElementsByTagName("entry");

	        for (int i = 0; i < elementos.getLength(); i++) {
	            System.out.println("-------- SITIO TURISTICO --------");
				SitioTuristico sitioTuristico = new SitioTuristico();
	            Node elemento = elementos.item(i);

	            if (elemento.getNodeType() == Node.ELEMENT_NODE) {
	                Element sitio = (Element) elemento;
	                String titulo = sitio.getElementsByTagName("title").item(0).getTextContent();
	                sitioTuristico.setTitulo(titulo);
	                System.out.println(titulo);
	                String wikipediaString = sitio.getElementsByTagName("wikipediaUrl").item(0).getTextContent();
	                System.out.println(wikipediaString);
	                
	                String wikipediaString2 = "https://es.dbpedia.org/data/"
	                        + wikipediaString.substring(wikipediaString.lastIndexOf("/") + 1) + ".json";
	                System.out.println(wikipediaString2);
	                
	                InputStreamReader fuente = new InputStreamReader(new java.net.URL(wikipediaString2).openStream());
	                JsonReader jsonReader = Json.createReader(fuente);
	                JsonObject obj = jsonReader.readObject();
	                String wikipediaString3 = "http://es.dbpedia.org/resource/"
	                        + titulo.replace(" ", "_");
	                System.out.println(wikipediaString3);
	                JsonObject objJSON = obj.getJsonObject(wikipediaString3);
	                
	                String resumenProperty = "http://dbpedia.org/ontology/abstract";
	                JsonArray propiedad = objJSON.getJsonArray(resumenProperty);
	                if(propiedad != null){
	                    System.out.println("-------- " + resumenProperty + " --------");
	                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
	                
	                        if (info.containsKey("type"))
	                        System.out.println("Type: " + info.getString("type"));
	        
	                        if (info.containsKey("value")) {
	                            System.out.println("Value: " + info.getString("value"));
	                            sitioTuristico.setResumen(resumenProperty);
	                        }
	                    
	                    }
	                }

	                String categoriaProperty = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	                propiedad = objJSON.getJsonArray(categoriaProperty);
	                if(propiedad != null){
	                    System.out.println("-------- " + categoriaProperty + " --------");
	                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
	                
	                        if (info.containsKey("type"))
	                        System.out.println("Type: " + info.getString("type"));
	        
	                        if (info.containsKey("value")) {
	                            System.out.println("Value: " + info.getString("value"));
	                            String cadena = info.getString("value");
	                            String categoria = cadena.substring(cadena.lastIndexOf("/") + 1);
	                            sitioTuristico.getCategorias().add(categoria);
	                        }
	                    
	                    }
	                }

	                String externalLinkProperty = "http://dbpedia.org/ontology/wikiPageExternalLink";
	                propiedad = objJSON.getJsonArray(externalLinkProperty);
	                if(propiedad != null){
	                    System.out.println("-------- " + externalLinkProperty + " --------");
	                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
	                
	                        if (info.containsKey("type"))
	                        System.out.println("Type: " + info.getString("type"));
	        
	                        if (info.containsKey("value")){
	                            System.out.println("Value: " + info.getString("value"));
	                            String link = info.getString("value");
	                            sitioTuristico.getEnlacesExternos().add(link);
	                        }
	                    
	                    
	                    }
	                }

	                String imagenProperty = "http://es.dbpedia.org/property/imagen";
	                propiedad = objJSON.getJsonArray(imagenProperty);
	                if(propiedad != null){
	                    System.out.println("-------- " + imagenProperty + " --------");
	                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
	                
	                        if (info.containsKey("type"))
	                        System.out.println("Type: " + info.getString("type"));
	        
	                        if (info.containsKey("value")){
	                            System.out.println("Value: " + info.getString("value"));
	                            String imagen = info.getString("value");
	                            sitioTuristico.getImagenes().add(imagen);
	                        }
	                    }
	                }
	                sitiosTuristicos.insertOne(sitioTuristico);
	                System.out.println("Sitio Id asignado: " + sitioTuristico.getId());
	                restaurante.getSitios().add(sitioTuristico);
	            }
	        }
	        //Filtramos por id del restaurante para actualizarlo
	        org.bson.Document filterByRestauranteId = new org.bson.Document("_id", restaurante.getId());
            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            
            Restaurante updatedRestaurante = restaurantes.findOneAndReplace(filterByRestauranteId, restaurante, returnDocAfterReplace);
            System.out.println("Grade replaced:\t" + updatedRestaurante);


		}
		
		
    }

}
