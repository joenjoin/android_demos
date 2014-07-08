package org.youdian.android_demos.xml;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

/*
 * 三种解析方式的优缺点
 * 1.SAX 逐行读取，节省内存，适用于固定格式的XML 效率高
 * 2.DOM 整体载入，消耗内存，适用于需要动态修改的XML 效率低
 * 3.Pull 逐行读取  效率高    android系统解析res下的资源文件即使用XmlPullParser方式
 * 
 */
public class XmlParsers {
	private static final String TAG = "XMLParser";

	public interface Parser<T> {
		public List<T> parse(InputStream xml) throws Exception;

		public String serialize(List<T> list) throws Exception;

	}

	public static class SAXParser implements Parser<Person> {

		@Override
		public List<Person> parse(InputStream xml) throws Exception {
			// TODO Auto-generated method stub
			final List<Person> list = new ArrayList<Person>();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			javax.xml.parsers.SAXParser parser = factory.newSAXParser();
			parser.parse(xml, new DefaultHandler() {

				StringBuilder builder;
				Person t;

				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					// TODO Auto-generated method stub
					super.characters(ch, start, length);
					builder.append(ch, start, length);
					Log.d(TAG, "ch=" + builder.toString());
				}

				@Override
				public void endDocument() throws SAXException {
					// TODO Auto-generated method stub
					super.endDocument();
				}

				@Override
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					// TODO Auto-generated method stub
					super.endElement(uri, localName, qName);
					if (localName.equals("id")) {
						t.setId(Integer.parseInt(builder.toString()));
					} else if (localName.equals("name")) {
						t.setName(builder.toString());
					} else if (localName.equals("age")) {
						t.setAge(Integer.parseInt(builder.toString()));
					} else if (localName.equals("person")) {
						list.add(t);
					}
				}

				@Override
				public void startDocument() throws SAXException {
					// TODO Auto-generated method stub
					super.startDocument();
					builder = new StringBuilder();
				}

				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					// TODO Auto-generated method stub
					super.startElement(uri, localName, qName, attributes);
					if (localName.equals("person")) {
						t = new Person();
					}
					builder.setLength(0);
				}

			});
			return list;
		}

		@Override
		public String serialize(List<Person> list) throws Exception {
			// TODO Auto-generated method stub
			SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory
					.newInstance();
			TransformerHandler handler = factory.newTransformerHandler();
			Transformer transformer = handler.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);
			handler.setResult(result);
			String uri = "";
			String localName = "";
			handler.startDocument();
			handler.startElement(uri, localName, "persons", null);
			AttributesImpl attrs = new AttributesImpl();
			char[] ch = null;
			for (Person p : list) {
				attrs.clear();
				attrs.addAttribute(uri, localName, "id", "string",
						String.valueOf(p.getId()));
				handler.startElement(uri, localName, "person", attrs);
				handler.startElement(uri, localName, "name", null);
				ch = p.getName().toCharArray();
				handler.characters(ch, 0, ch.length);
				handler.endElement(uri, localName, "name");
				handler.startElement(uri, localName, "age", null);
				ch = String.valueOf(p.getAge()).toCharArray();
				handler.characters(ch, 0, ch.length);
				handler.endElement(uri, localName, "age");
				handler.endElement(uri, localName, "person");
			}
			handler.endElement(uri, localName, "persons");
			handler.endDocument();
			return writer.toString();
		}

	}

	public static class DOMParser implements Parser<Person> {

		@Override
		public List<Person> parse(InputStream xml) throws Exception {
			// TODO Auto-generated method stub
			List<Person> list = new ArrayList<Person>();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xml);
			Element rootElement = doc.getDocumentElement();
			NodeList nodes = rootElement.getElementsByTagName("person");
			for (int i = 0; i < nodes.getLength(); i++) {
				Person p = new Person();
				Node node = nodes.item(i);
				NodeList properties = node.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("id")) {
						int id = Integer.parseInt(property.getFirstChild()
								.getNodeValue());
						p.setId(id);
					} else if (nodeName.equals("name")) {
						String name = property.getFirstChild().getNodeValue();
						p.setName(name);
					} else if (nodeName.equals("age")) {
						int age = Integer.parseInt(property.getFirstChild()
								.getNodeValue());
						p.setAge(age);
					}
				}
				list.add(p);
			}
			return list;
		}

		@Override
		public String serialize(List<Person> list) throws Exception {
			// TODO Auto-generated method stub
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element rootElement = doc.createElement("persons");
			for (Person p : list) {
				Element personElement = doc.createElement("person");
				personElement.setAttribute("id", String.valueOf(p.getId()));
				Element nameElement = doc.createElement("name");
				nameElement.setTextContent(p.getName());
				personElement.appendChild(nameElement);
				Element ageElement = doc.createElement("age");
				ageElement.setTextContent(String.valueOf(p.getAge()));
				personElement.appendChild(ageElement);
				rootElement.appendChild(personElement);
			}
			doc.appendChild(rootElement);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			StringWriter writer = new StringWriter();
			Source source = new DOMSource(doc);
			Result result = new StreamResult(writer);
			transformer.transform(source, result);
			return writer.toString();
		}

	}

	public static class PullParser implements Parser<Person> {

		@Override
		public List<Person> parse(InputStream xml) throws Exception {
			// TODO Auto-generated method stub
			List<Person> list = null;
			Person p = null;
			XmlPullParser xpp = Xml.newPullParser();
			xpp.setInput(xml, "UTF-8");
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {

				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<Person>();
					break;
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					if (name.equals("person")) {
						p = new Person();
					} else if (name.equals("id")) {
						String id = xpp.nextText();
						p.setId(Integer.parseInt(id));
					} else if (name.equals("name")) {
						String pName = xpp.nextText();
						p.setName(pName);
					} else if (name.equals("age")) {
						String age = xpp.nextText();
						p.setAge(Integer.parseInt(age));
					}
					break;
				case XmlPullParser.END_TAG:
					String tagName = xpp.getName();
					if (tagName.equals("person")) {
						list.add(p);
						p = null;
					}
					break;

				}
				eventType = xpp.next();
			}
			return list;
		}

		@Override
		public String serialize(List<Person> list) throws Exception {
			// TODO Auto-generated method stub
			XmlSerializer serializer = Xml.newSerializer();
			StringWriter writer = new StringWriter();
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "persons");
			for (Person p : list) {
				serializer.startTag("", "person");
				serializer.attribute("", "id", String.valueOf(p.getId()));
				serializer.startTag("", "name");
				serializer.text(p.getName());
				serializer.endTag("", "name");
				serializer.startTag("", "age");
				serializer.text(String.valueOf(p.getAge()));
				serializer.endTag("", "age");
				serializer.endTag("", "person");
			}
			serializer.endTag("", "persons");
			serializer.endDocument();
			return writer.toString();
		}

	}

}
