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

import org.w3c.dom.Document;
import org.xml.sax.InputSource;


public class ParseUtils {
	
	//private static final Logger LOGGER = Logger.getLogger(ParseUtils.class);
	
	@SuppressWarnings("unchecked")
	public static Object parseXmlToObject(String xml, Object object){

		try {			
			
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			object =  jaxbUnmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
					
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return object;
		
	}
	
	 private static Document convertStringToDocument(String xmlStr) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder;  
	        try  
	        {  
	            builder = factory.newDocumentBuilder();  
	            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
	            return doc;
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        return null;
	    }

}
