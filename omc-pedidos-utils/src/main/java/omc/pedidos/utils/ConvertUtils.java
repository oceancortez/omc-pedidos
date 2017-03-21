/**
 * 
 */
package omc.pedidos.utils;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ConvertUtils {

	// private static final Logger LOGGER = Logger.getLogger(ParseUtils.class);

	@SuppressWarnings("unchecked")
	public static Object parseXmlToObject(String xml, Object object) {

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			object = jaxbUnmarshaller.unmarshal(new StreamSource(new StringReader(xml)));

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;

	}

	private static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object convertJsonToObject(final String json, Object object) {
		Object obj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			obj = mapper.readValue(json, object.getClass());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static Object convertJsonNodeToObject(final String json, Object object) {

		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
		// true);

		try {
			object = mapper.readValue(json, object.getClass());

			JsonNode jsonNode = mapper.readTree(json);
			if (jsonNode.findValue("pedidos").isArray()) {
				ArrayNode arrayNode = (ArrayNode) jsonNode.findValue("pedidos");
			} else {
				// object =
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

}
