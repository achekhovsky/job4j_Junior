package ru.job4j.jdbc.magnet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CustomParser extends DefaultHandler {
	private int count;
	
	public CustomParser() {
		
	}
	
	@Override
	public void startElement(String uri,
            String localName,
            String qName,
            Attributes attributes) throws SAXException {
		if ("entry".equals(qName)) {
			count += Integer.parseInt(attributes.getValue("field"));
		}
	}
	
	@Override
	public void startDocument()  throws SAXException {
		count = 0;
	}
	
	@Override
	public void endDocument()  throws SAXException {
		System.out.print(count);
	}
           
}
