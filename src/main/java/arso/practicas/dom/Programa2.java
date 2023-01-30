package arso.practicas.dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Programa2 {
	public static void main(String[] args) throws Exception {

		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		DocumentBuilder analizador = factoria.newDocumentBuilder();

		// 3. Analizar el documento
		Document documento = analizador.parse("xml/acta.xml");

		NodeList elementos = documento.getElementsByTagName("nota");
		
		for (int i = 0; i < elementos.getLength(); i++) {
			Element nota = (Element) elementos.item(i);
			System.out.println(nota.getParentNode().getNodeName());
			
			if (nota.getParentNode().getNodeName().equals("calificacion")) {
				String numeroTexto = nota.getTextContent();
				double numero = Double.parseDouble(numeroTexto);
			}

		}
		
		System.out.println("fin");
	}

}
