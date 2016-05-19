
package com.netbows.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Visto en http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580
 *
 * @author Argotec
 */
public class XMLUtils {

    public static Node stringToMessageNode(String message) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(message)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return null;
        }
        NodeList root = document.getChildNodes();
        Node messg = XMLUtils.getNode("message", root);

        return messg;
    }

    public static Node getNode(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                return node;
            }
        }
        return null;
    }

    public static String getNodeValue(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int x = 0; x < childNodes.getLength(); x++) {
            Node data = childNodes.item(x);
            if (data.getNodeType() == Node.TEXT_NODE) {
                return data.getNodeValue();
            }
        }
        return "";
    }

    public static String getNodeValue(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.TEXT_NODE) {
                        return data.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public static String getNodeAttr(String attrName, Node node) {
        NamedNodeMap attrs = node.getAttributes();
        for (int y = 0; y < attrs.getLength(); y++) {
            Node attr = attrs.item(y);
            if (attr.getNodeName().equalsIgnoreCase(attrName)) {
                return attr.getNodeValue();
            }
        }
        return "";
    }

    public static String getNodeAttr(String tagName, String attrName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.ATTRIBUTE_NODE) {
                        if (data.getNodeName().equalsIgnoreCase(attrName)) {
                            return data.getNodeValue();
                        }
                    }
                }
            }
        }
        return "";
    }

    // Specific methods for this project
    public static String getOperationName (String message){
        Node messg = stringToMessageNode(message);
        Node payload = getNode("payload",messg.getChildNodes());
        Node operation = getNode("operation",payload.getChildNodes());
        
        return getNodeValue("name",operation.getChildNodes());        
    }
    
    public static String getDeviceName(String message) {
        Node messg = stringToMessageNode(message);
        Node payload = getNode("payload",messg.getChildNodes());
        Node operation = getNode("operation",payload.getChildNodes());
        Node parameters = getNode("parameters",operation.getChildNodes());
        
        String deviceID = "MalformedXML";
        for (int i = 0; i < parameters.getChildNodes().getLength(); i++){
            if (getNodeValue("typeInput",parameters.getChildNodes().item(i).getChildNodes()).equals("deviceID")){
                deviceID = getNodeValue("value",parameters.getChildNodes().item(i).getChildNodes());
            }
        }
        return deviceID;
        
    }
    public static ArrayList<String> getParameters(String message){
        Node messg = stringToMessageNode(message);
        Node payload = getNode("payload",messg.getChildNodes());
        Node operation = getNode("operation",payload.getChildNodes());
        Node parameters = getNode("parameters",operation.getChildNodes());
        
        ArrayList<String> parametersList = new ArrayList();
        for (int i = 0; i < parameters.getChildNodes().getLength(); i++){
            if (getNodeValue("typeInput",parameters.getChildNodes().item(i).getChildNodes()).equals("action")){
                parametersList.add(getNodeValue("value",parameters.getChildNodes().item(i).getChildNodes()));
            }
        }
        return parametersList;
    }

}
