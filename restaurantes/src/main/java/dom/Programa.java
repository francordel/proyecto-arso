package dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.FileReader;
import java.io.InputStreamReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Programa {

    public static String getUrlByPostalCode(String postalCode) {
        String url = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + postalCode
                + "&country=ES&radius=10&username=arso&lang=es";
        return url;
    }

    public static void main(String[] args) throws Exception {
        // 1. Obtener una factoría
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

        // 2. Pedir a la factoría la construcción del analizador
        DocumentBuilder analizador = factoria.newDocumentBuilder();

        String url = getUrlByPostalCode("30003");

        Document documento = analizador.parse(url);

        NodeList elementos = documento.getElementsByTagName("entry");

        for (int i = 0; i < elementos.getLength(); i++) {
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
                JsonArray propiedad = objJSON.getJsonArray("http://dbpedia.org/ontology/abstract");
                if(propiedad != null){
                    for (JsonObject info : propiedad.getValuesAs(JsonObject.class)) { 
                
                        if (info.containsKey("type"))
                        System.out.println("Type: " + info.getString("type"));
        
                        if (info.containsKey("value"))
                            System.out.println("Value: " + info.getString("value"));
                    
                    
                    }
                }

            }
        }
    }

}
