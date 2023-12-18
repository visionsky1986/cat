/* THIS FILE WAS AUTO GENERATED BY codegen-maven-plugin, DO NOT EDIT IT */
package com.dianping.cat.configuration.model.transform;


import static com.dianping.cat.configuration.model.Constants.ENTITY_CONFIG;
import static com.dianping.cat.configuration.model.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.configuration.model.Constants.ENTITY_HOST;
import static com.dianping.cat.configuration.model.Constants.ENTITY_PROPERTY;
import static com.dianping.cat.configuration.model.Constants.ENTITY_SERVER;
import static com.dianping.cat.configuration.model.Constants.ENTITY_PROPERTIES;
import static com.dianping.cat.configuration.model.Constants.ENTITY_SERVERS;

import java.io.IOException;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.dianping.cat.configuration.model.IEntity;
import com.dianping.cat.configuration.model.entity.ClientConfig;
import com.dianping.cat.configuration.model.entity.Domain;
import com.dianping.cat.configuration.model.entity.Host;
import com.dianping.cat.configuration.model.entity.Property;
import com.dianping.cat.configuration.model.entity.Server;

public class DefaultXmlParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultXmlMaker m_maker = new DefaultXmlMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private IEntity<?> m_root;

   private StringBuilder m_text = new StringBuilder(256);

   @SuppressWarnings("unchecked")
   public <T extends IEntity<?>> T parse(Class<T> entityType, InputSource input) throws IOException {
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();

         factory.setValidating(false);
         factory.setFeature("http://xml.org/sax/features/validation", false);
         factory.newSAXParser().parse(input, this);

         m_linker.finish();

         if (entityType.isAssignableFrom(m_root.getClass())) {
            return (T) m_root;
         } else {
            throw new IllegalArgumentException(String.format("Expected %s, but was %s", entityType, m_root.getClass()));
         }
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX Parser! " + e, e);
      } catch (SAXException e) {
         throw new IOException("Unable to parse XML! " + e, e);
      }
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
         return defaultValue;
      }

      if (type == Boolean.class) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   @Override
   public void characters(char[] ch, int start, int length) throws SAXException {
      m_text.append(ch, start, length);
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (uri == null || uri.length() == 0) {
         Object currentObj = m_objs.pop();

         m_tags.pop();

         if (currentObj instanceof Property) {
            Property property = (Property) currentObj;

            property.setValue(getText());
         }
      }

      m_text.setLength(0);
   }

   protected String getText() {
      return m_text.toString();
   }

   private void parseForConfig(ClientConfig parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_SERVERS.equals(qName) || ENTITY_PROPERTIES.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_HOST.equals(qName)) {
         Host host = m_maker.buildHost(attributes);

         m_linker.onHost(parentObj, host);
         m_objs.push(host);
      } else if (ENTITY_DOMAIN.equals(qName)) {
         Domain domain = m_maker.buildDomain(attributes);

         m_linker.onDomain(parentObj, domain);
         m_objs.push(domain);
      } else if (ENTITY_SERVER.equals(qName)) {
         Server server = m_maker.buildServer(attributes);

         m_linker.onServer(parentObj, server);
         m_objs.push(server);
      } else if (ENTITY_PROPERTY.equals(qName)) {
         Property property = m_maker.buildProperty(attributes);

         m_linker.onProperty(parentObj, property);
         m_objs.push(property);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under config!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForDomain(Domain parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForHost(Host parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForProperty(Property parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForServer(Server parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseRoot(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_CONFIG.equals(qName)) {
         ClientConfig config = m_maker.buildConfig(attributes);

         m_root = config;
         m_objs.push(config);
         m_tags.push(qName);
      } else if (ENTITY_HOST.equals(qName)) {
         Host host = m_maker.buildHost(attributes);

         m_root = host;
         m_objs.push(host);
         m_tags.push(qName);
      } else if (ENTITY_DOMAIN.equals(qName)) {
         Domain domain = m_maker.buildDomain(attributes);

         m_root = domain;
         m_objs.push(domain);
         m_tags.push(qName);
      } else if (ENTITY_SERVER.equals(qName)) {
         Server server = m_maker.buildServer(attributes);

         m_root = server;
         m_objs.push(server);
         m_tags.push(qName);
      } else if (ENTITY_PROPERTY.equals(qName)) {
         Property property = m_maker.buildProperty(attributes);

         m_root = property;
         m_objs.push(property);
         m_tags.push(qName);
      } else {
         throw new SAXException("Unknown root element(" + qName + ") found!");
      }
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (uri == null || uri.length() == 0) {
         if (m_objs.isEmpty()) { // root
            parseRoot(qName, attributes);
         } else {
            Object parent = m_objs.peek();
            String tag = m_tags.peek();

            if (parent instanceof ClientConfig) {
               parseForConfig((ClientConfig) parent, tag, qName, attributes);
            } else if (parent instanceof Host) {
               parseForHost((Host) parent, tag, qName, attributes);
            } else if (parent instanceof Domain) {
               parseForDomain((Domain) parent, tag, qName, attributes);
            } else if (parent instanceof Server) {
               parseForServer((Server) parent, tag, qName, attributes);
            } else if (parent instanceof Property) {
               parseForProperty((Property) parent, tag, qName, attributes);
            } else {
               throw new RuntimeException(String.format("Unknown entity(%s) under %s!", qName, parent.getClass().getName()));
            }
         }

         m_text.setLength(0);
        } else {
         throw new SAXException(String.format("Namespace(%s) is not supported by %s.", uri, this.getClass().getName()));
      }
   }
}
