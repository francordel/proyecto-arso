package arso.practicas.dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Programa3 {
	public static void main(String[] args) throws Exception {

		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		DocumentBuilder analizador = factoria.newDocumentBuilder();

		// 3. Analizar el documento
		Document documento = analizador.parse("xml/acta.xml");

		NodeList elementos = documento.getElementsByTagName("calificacion");
		
		for (int i = 0; i < elementos.getLength(); i++) {
			Element calificacion = (Element) elementos.item(i);
			
			Element nota =
					(Element) calificacion.getElementsByTagName("nota");
			
			String numeroTexto = nota.getTextContent();
			Double numero = Double.parseDouble(numeroTexto);
		}
		
		System.out.println("fin");
	}

}
