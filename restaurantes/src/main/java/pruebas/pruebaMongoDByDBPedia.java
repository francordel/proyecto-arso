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
/*	
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

	            Node elemento = elementos.item(i);

	            if (elemento.getNodeType() == Node.ELEMENT_NODE) {
	                Element sitio = (Element) elemento;
	                String titulo = sitio.getElementsByTagName("title").item(0).getTextContent();
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
	        
	                        if (info.containsKey("value"))
	                            System.out.println("Value: " + info.getString("value"));
	                    
	                    
	                    }
	                }

	                String categoriaProperty = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	                propiedad = objJSON.getJsonArray(categoriaProperty);
	                if(propiedad != null){
	                    System.out.println("-------- " + categoriaProperty + " --------");
	                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
	                
	                        if (info.containsKey("type"))
	                        System.out.println("Type: " + info.getString("type"));
	        
	                        if (info.containsKey("value"))
	                            System.out.println("Value: " + info.getString("value"));
	                    
	                    
	                    }
	                }

	                String externalLinkProperty = "http://dbpedia.org/ontology/wikiPageExternalLink";
	                propiedad = objJSON.getJsonArray(externalLinkProperty);
	                if(propiedad != null){
	                    System.out.println("-------- " + externalLinkProperty + " --------");
	                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
	                
	                        if (info.containsKey("type"))
	                        System.out.println("Type: " + info.getString("type"));
	        
	                        if (info.containsKey("value"))
	                            System.out.println("Value: " + info.getString("value"));
	                    
	                    
	                    }
	                }

	                String imagenProperty = "http://es.dbpedia.org/property/imagen";
	                propiedad = objJSON.getJsonArray(imagenProperty);
	                if(propiedad != null){
	                    System.out.println("-------- " + imagenProperty + " --------");
	                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
	                
	                        if (info.containsKey("type"))
	                        System.out.println("Type: " + info.getString("type"));
	        
	                        if (info.containsKey("value"))
	                            System.out.println("Value: " + info.getString("value").replace(" ", "_"));
	                    
	                    }
	                }


	            }
	        }
		}
		
		
    }

}
