package dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class Programa {

    public static String getUrlByPostalCode(String postalCode) {
        String url = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + postalCode + "&country=ES&radius=10&username=arso";
        return url;
    }

    public static void main(String[] args) throws Exception {
            // 1. Obtener una factoría
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

            // 2. Pedir a la factoría la construcción del analizador
            DocumentBuilder analizador = factoria.newDocumentBuilder();

            String url = getUrlByPostalCode("30579");

            Document documento = analizador.parse(url);

            NodeList elementos = documento.getElementsByTagName("entry");

            for (int i = 0; i < elementos.getLength(); i++) {
                Node elemento = elementos.item(i);

                if(elemento.getNodeType() == Node.ELEMENT_NODE) {
                    Element sitio = (Element) elemento;
                    String titulo = sitio.getElementsByTagName("title").item(0).getTextContent();
                    System.out.println(titulo);
                }
            }
            System.out.println("fin");
    }

}
