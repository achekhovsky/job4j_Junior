package ru.job4j.jdbc.magnet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

public class StoreXML {
	private static Logger log = Logger.getLogger(StoreXML.class.getName());
	private File store;
	
	public StoreXML(File target) {
		this.store = target;
	}
	
	@XmlRootElement
	public static class Entries {
		private List<Entry> entry;

		public Entries() {
			this.entry = new ArrayList<>();
		}

		public Entries(List<Entry> entries) {
			this.entry = entries;
		}

		public List<Entry> getEntry() {
			return entry;
		}

		public void setEntry(List<Entry> entries) {
			this.entry = entries;
		}

		public void addEntry(Entry entry) {
			this.entry.add(entry);
		}
	}
    
	@XmlRootElement
    public static class Entry {
        private int field;

        public Entry() {
        }
        
        public Entry(int value) {
            this.field = value;
        }

        public int getField() {
            return field;
        }

        public void setField(int value) {
            this.field = value;
        }
    }
    
    public void save(Entries ents) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(ents, this.store);
		} catch (JAXBException e) {
			log.log(Level.SEVERE, "StoreXML::save ", e);
		}
        
    }
    
    public void convert(File source, File dest, File scheme) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(scheme));
			transformer.transform(new StreamSource(source), new StreamResult(dest));
		} catch (TransformerException e) {
			log.log(Level.SEVERE, "StoreXML::convert ", e);
		}
    }
    
    public void load(File source) {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
			SAXParser sp = spf.newSAXParser();
			sp.parse(source, new CustomParser());
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, "StoreXML::load ", e);
		}	
    }
}
