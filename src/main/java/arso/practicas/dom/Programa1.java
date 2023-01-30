package arso.practicas.dom;

import java.time.LocalDate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Programa1 {
	public static void main(String[] args) throws Exception {

		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		DocumentBuilder analizador = factoria.newDocumentBuilder();

		// 3. Analizar el documento
		Document documento = analizador.parse("xml/acta.xml");

		NodeList elementos = documento.getElementsByTagName("diligencia");
		double cont = 0;
		for (int i = 0; i < elementos.getLength(); i++) {
			Element elemento = (Element) elementos.item(i);
			String atributo1 = "fecha";
			
			LocalDate ob = LocalDate.parse(elemento.getAttribute(atributo1));
			if (ob.getMonth().equals(LocalDate.now().getMonth())) {
				cont++;
			}
		}
		
		System.out.println(elementos.getLength());
		System.out.println("Número de diligencias en este mes: " + cont);

		System.out.println("fin");
	}

}
