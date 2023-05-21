package restaurantes.servicio;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.InputStreamReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import restaurantes.modelo.EventoNuevaValoracion;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import restaurantes.modelo.Valoracion;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;

public class ServicioRestaurantes implements IServicioRestaurantes {

	Document documento;
	DocumentBuilder analizador;
	InputStreamReader fuente;

	private Repositorio<Restaurante, String> repositorio = FactoriaRepositorios.getRepositorio(Restaurante.class);
	
	private Connection connection;
	private Channel channel;

	public ServicioRestaurantes() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUri("amqps://lsbdhozw:nIyxGm7_zrPRixhO_iY0rM9Ptrqfw9U0@whale.rmq.cloudamqp.com/lsbdhozw");
			
			this.connection = factory.newConnection();
			this.channel = connection.createChannel();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void subscribeToRabbitMQ() {
		try {
			final String exchangeName = "evento.nueva.valoracion";
			final String queueName = "valoracion-queue";
			final String bindingKey = "";

			boolean durable = true;
			boolean exclusive = false;
			boolean autodelete = false;

			Map<String, Object> properties = null; // sin propiedades

			channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);
			channel.queueBind(queueName, exchangeName, bindingKey);

			boolean autoAck = false;

			String etiquetaConsumidor = "servicio-restaurantes";

			// Consumidor push

			channel.basicConsume(queueName, autoAck, etiquetaConsumidor,
					new DefaultConsumer(channel) {
						@Override
						public void handleDelivery(String consumerTag, Envelope envelope,
								AMQP.BasicProperties properties, byte[] body) throws IOException {

							long deliveryTag = envelope.getDeliveryTag();

							String contenido = new String(body);

							ObjectMapper mapper = new ObjectMapper(); // Jackson

							EventoNuevaValoracion evento = mapper.readValue(contenido, EventoNuevaValoracion.class);
							
							try {
								processEvent(evento);
							} catch (RepositorioException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (EntidadNoEncontrada e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// Confirma el procesamiento
							channel.basicAck(deliveryTag, false);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void processEvent(EventoNuevaValoracion evento) throws RepositorioException, EntidadNoEncontrada {
		List<Restaurante> restaurantes = repositorio.getAll();
	    for (Restaurante restaurante : restaurantes) {
	        if (restaurante.getIdOpinion().equals(evento.getIdOpinion())) {
	            restaurante.setCalificacionMedia(evento.getCalificacionMedia());
	            restaurante.setNumValoraciones(evento.getNumeroValoraciones());
	    		repositorio.update(restaurante);
	            break;
	        }
	    }
	    
	}

	@Override
	public String create(String nombre, String codigoPostal, String coordenadas, String idGestor)
			throws RepositorioException {
		Restaurante restaurante = new Restaurante();
		restaurante.setIdGestor(idGestor);
		restaurante.setNombre(nombre);
		restaurante.setCodigoPostal(codigoPostal);
		restaurante.setCoordenadas(coordenadas);

		return repositorio.add(restaurante);
	}

	@Override
	public void update(String id, String nombre, String codigoPostal, String coordenadas)
			throws RepositorioException, EntidadNoEncontrada {

		// DUDA CON PUT: ¿QUÉ PASA SI QUIERES CAMBIAR SOLO UNO DE LOS CAMPOS Y QUEDAN
		// CAMPOS VACÍOS?
		// ¿QUÉ LE LLEGA AL PUT? CADENA VACÍA, NULL...?

		Restaurante restaurante = repositorio.getById(id);

		restaurante.setNombre(nombre);
		restaurante.setCodigoPostal(codigoPostal);
		restaurante.setCoordenadas(coordenadas);

		repositorio.update(restaurante);
	}

	private String getUrlByPostalCode(String postalCode) {
		String url = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + postalCode
				+ "&country=ES&radius=10&username=arso&lang=es";
		return url;
	}

	private JsonArray getJsonArray(JsonObject obj, String property) {
		if (obj.containsKey(property) && !obj.isNull(property)) {
			return obj.getJsonArray(property);
		} else {
			return null;
		}
	}

	private String getValue(JsonObject info) {
		JsonValue.ValueType type = info.get("value").getValueType();
		if (type == JsonValue.ValueType.STRING) {
			return info.getString("value");
		} else if (type == JsonValue.ValueType.NUMBER) {
			return String.valueOf(info.getInt("value"));
		} else {
			return "";
		}
	}

	@Override
	public List<SitioTuristico> getSitiosProximos(String id) throws RepositorioException, EntidadNoEncontrada {
		Restaurante restaurante = repositorio.getById(id);
		List<SitioTuristico> sitios = new LinkedList<SitioTuristico>();

		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		try {
			analizador = factoria.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = getUrlByPostalCode(restaurante.getCodigoPostal());

		try {
			documento = analizador.parse(url);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NodeList elementos = documento.getElementsByTagName("entry");

		for (int i = 0; i < elementos.getLength(); i++) {

			Node elemento = elementos.item(i);

			if (elemento.getNodeType() == Node.ELEMENT_NODE) {
				Element sitioElement = (Element) elemento;
				SitioTuristico sitio = new SitioTuristico();
				sitio.setTitulo(sitioElement.getElementsByTagName("title").item(0).getTextContent());

				String wikipediaUrl = sitioElement.getElementsByTagName("wikipediaUrl").item(0).getTextContent();
				String dbpediaUrl = "https://es.dbpedia.org/data/"
						+ wikipediaUrl.substring(wikipediaUrl.lastIndexOf("/") + 1) + ".json";

				// Leer JSON desde DBpedia
				try {
					fuente = new InputStreamReader(new java.net.URL(dbpediaUrl).openStream());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JsonReader jsonReader = Json.createReader(fuente);
				JsonObject obj = jsonReader.readObject();

				String wikiResource = "http://es.dbpedia.org/resource/" + sitio.getTitulo().replace(" ", "_");
				JsonObject objJSON = obj.getJsonObject(wikiResource);

				// Obtener resumen
				String resumenProperty = "http://dbpedia.org/ontology/abstract";
				JsonArray resumenArray = getJsonArray(objJSON, resumenProperty);
				if (resumenArray != null) {
					for (JsonObject info : resumenArray.getValuesAs(JsonObject.class)) {
						String resumen = getValue(info);
						sitio.setResumen(resumen);
					}
				}

				// Obtener categorias
				String categoriaProperty = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
				JsonArray categoriaArray = getJsonArray(objJSON, categoriaProperty);
				if (categoriaArray != null) {
					List<String> categorias = new LinkedList<String>();
					for (JsonObject info : categoriaArray.getValuesAs(JsonObject.class)) {
						String categoria = getValue(info);
						categorias.add(categoria);
					}
					sitio.setCategorias(categorias);
				}

				// Obtener enlaces externos
				String externalLinkProperty = "http://dbpedia.org/ontology/wikiPageExternalLink";
				JsonArray externalLinkArray = getJsonArray(objJSON, externalLinkProperty);
				if (externalLinkArray != null) {
					List<String> enlacesExternos = new LinkedList<String>();
					for (JsonObject info : externalLinkArray.getValuesAs(JsonObject.class)) {
						String enlaceExterno = getValue(info);
						enlacesExternos.add(enlaceExterno);
					}
					sitio.setEnlacesExternos(enlacesExternos);
				}

				// Obtener imagen
				String imagenProperty = "http://es.dbpedia.org/property/imagen";
				JsonArray imagenArray = getJsonArray(objJSON, imagenProperty);
				if (imagenArray != null) {
					List<String> imagenes = new LinkedList<String>();
					for (JsonObject info : imagenArray.getValuesAs(JsonObject.class)) {
						String imagen = getValue(info).replace(" ", "_");
						imagenes.add(imagen);
					}
					sitio.setImagenes(imagenes);
				}

				sitios.add(sitio);
			}
		}
		return sitios;
	}

	@Override
	public void setSitiosDestacados(String id, List<SitioTuristico> sitios)
			throws RepositorioException, EntidadNoEncontrada {

		Restaurante restaurante = repositorio.getById(id);

		restaurante.setSitios(sitios);
		
		repositorio.update(restaurante);
	}

	@Override
	public void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {
		Restaurante restaurante = repositorio.getById(id);

		List<Plato> platos = restaurante.getPlatos();

		for (Plato platoExistente : platos) {
			if (platoExistente.getNombre().equals(plato.getNombre())) {
				throw new IllegalArgumentException("Ya existe un plato con este nombre");
			}
		}

		platos.add(plato);
		restaurante.setPlatos(platos);

		repositorio.update(restaurante);
	}

	@Override
	public void removePlato(String id, String nombre) throws RepositorioException, EntidadNoEncontrada {

		Restaurante restaurante = repositorio.getById(id);

		List<Plato> platos = restaurante.getPlatos();
		Iterator<Plato> iterator = platos.iterator();

		while (iterator.hasNext()) {
			Plato plato = iterator.next();
			if (plato.getNombre().equals(nombre)) {
				iterator.remove();
				break;
			}
		}

		restaurante.setPlatos(platos);
		repositorio.update(restaurante);
	}

	@Override
	public void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {
		Restaurante restaurante = repositorio.getById(id);

		List<Plato> platos = restaurante.getPlatos();

		for (Plato platoExistente : platos) {
			if (platoExistente.getNombre().equals(plato.getNombre())) {
				platos.remove(platoExistente);
				platos.add(plato);
				restaurante.setPlatos(platos);
			}
		}

		repositorio.update(restaurante);
	}

	@Override
	public Restaurante getRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {
		return repositorio.getById(id);
	}

	@Override
	public void removeRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {
		Restaurante restaurante = repositorio.getById(id);

		repositorio.delete(restaurante);
	}

	@Override
	public List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException {

		LinkedList<RestauranteResumen> resultado = new LinkedList<>();

		for (String id : repositorio.getIds()) {
			try {
				Restaurante restaurante = getRestaurante(id);
				RestauranteResumen resumen = new RestauranteResumen();
				resumen.setId(restaurante.getId());
				resumen.setNombre(restaurante.getNombre());
				resumen.setCoordenadas(restaurante.getCoordenadas());
				resumen.setCodigoPostal(restaurante.getCodigoPostal());

				resultado.add(resumen);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}

	@Override
	public Boolean isGestor(String idRestaurante, String id) throws RepositorioException, EntidadNoEncontrada {
		Restaurante restaurante = repositorio.getById(idRestaurante);

		return id.equals(restaurante.getIdGestor());
	}

}
