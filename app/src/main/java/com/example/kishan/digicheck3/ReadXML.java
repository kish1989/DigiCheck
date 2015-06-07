package com.example.kishan.digicheck3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Kishan on 05-06-2015.
 */
public class ReadXML {
    public String getAadharUID(String xmlString) throws Exception
    {
        String aadharId = "";
        Document xmlResult;
        xmlResult = loadXMLFromString(xmlString);
        NodeList nodeList =  xmlResult.getElementsByTagName("PrintLetterBarcodeData");
        Node node = nodeList.item(0);
        if(node.getNodeType() == node.ELEMENT_NODE)
        {
            Element nodeElement = (Element) node;
            aadharId = nodeElement.getAttribute("uid");
            return aadharId;
        }
        else
        {
            return "Unique ID not found in the QR";
        }

    }

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(xml));

        return builder.parse(is);
    }
}
