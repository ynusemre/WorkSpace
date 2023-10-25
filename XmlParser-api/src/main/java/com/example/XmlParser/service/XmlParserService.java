package com.example.XmlParser.service;



import com.example.XmlParser.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class XmlParserService {

    public List<Message> messageXmlParsing(){
        List<Message> messages = new ArrayList<>();

        try {
            File xmlFile = new File("D:\\Desktop\\WorkSpace\\se-message.xml"); // XML dosyasının yolunu burada belirtin

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList messageNodes = document.getElementsByTagName("message");

            for (int i = 0; i < messageNodes.getLength(); i++) {
                Element messageElement = (Element) messageNodes.item(i);
                String xsiType = messageElement.getAttribute("xsi:type");
                String name = messageElement.getElementsByTagName("name").item(0).getTextContent();

                Message message = new Message(i,xsiType,name);

               /// messageRepository.save(message);
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public List<String> structureRefsXmlParsing(){
        List<String> structureRefs = new ArrayList<>();
        List<String> fields = new ArrayList<>();

        try {
            File xmlFile = new File("D:\\Desktop\\WorkSpace\\sedef.xml"); // XML dosyasının yolunu burada belirtin

            String filePath = "D:\\Desktop\\WorkSpace\\sedef.xml";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList structureNodes = document.getElementsByTagName("structure");

            String xmlContent = new String(Files.readAllBytes(Paths.get(filePath)));

            String patternString = "<fields>([\\s\\S]*?)</fields>";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(xmlContent);

            while (matcher.find()) {
                fields.add("<fields>" + matcher.group(1) + "</fields>");
            }

            for (int i = 0; i < structureNodes.getLength(); i++) {

                Element structureElement = (Element) structureNodes.item(i);

                String version="";
                String commentTag="";
                String namedStructNoTag="";
                String field="";

                String name = structureElement.getElementsByTagName("name").item(0).getTextContent();
                String iName = structureElement.getElementsByTagName("i-name").item(0).getTextContent();
                System.out.println(name);
                String initialTag="<structure>\n";

                if (structureElement.getElementsByTagName("version").getLength() > 0) {
                    NodeList versionNodes = document.getElementsByTagName("version");
                    Node versionNode = versionNodes.item(0);
                    if (versionNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element versionElement = (Element) versionNode;
                        version=getStringFromElement(versionElement)+"\n";
                    }
                }

                String nameTag="<name>" + name + "</name>\n";
                String iNameTag="<i-name>" + iName +"</i-name>\n";

                if (structureElement.getElementsByTagName("comments").getLength() > 0) {
                    String comment = structureElement.getElementsByTagName("comments").item(0).getTextContent();
                    commentTag="<comments>" + comment + "</comments>\n";
                }

                if (structureElement.getElementsByTagName("named_struct_no").getLength() > 0) {
                    String namedStructNo = structureElement.getElementsByTagName("named_struct_no").item(0).getTextContent();

                    namedStructNoTag="<named_struct_no>" + namedStructNo + "</named_struct_no>\n";
                }

                String finalMessageTag="</structure>\n";

                String allMessage=initialTag + version + nameTag + iNameTag + namedStructNoTag
                        + commentTag + fields.get(i) +finalMessageTag;

                //Field field1=new Field();
                //field1.setPart(allMessage);
                //fieldRepository.save(field1);

                structureRefs.add(allMessage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structureRefs;
    }

    public List<String> messageXmlParsing2() {

        List<String> messages = new ArrayList<>();

        try {
            File xmlFile = new File("D:\\Desktop\\WorkSpace\\se-message.xml"); // XML dosyasının yolunu burada belirtin

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList messageNodes = document.getElementsByTagName("message");

            int variableIndex = 0;
            int propertiesIndex = 0;

            for (int i = 0; i < messageNodes.getLength(); i++) {
                Element messageElement = (Element) messageNodes.item(i);

                String version="";
                String variable="";
                String property="";
                String structureRefTag="";

                String xsiType = messageElement.getAttribute("xsi:type");
                String name = messageElement.getElementsByTagName("name").item(0).getTextContent();
                String iName = messageElement.getElementsByTagName("i-name").item(0).getTextContent();

                String beginMessageTag="<message xsi:type=" + xsiType+">\n";

                if (messageElement.getElementsByTagName("version").getLength() > 0) {
                    NodeList variableNodes = document.getElementsByTagName("version");
                    Node variableNode = variableNodes.item(0);
                    if (variableNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element variableElement = (Element) variableNode;
                        version=getStringFromElement(variableElement)+"\n";
                    }
                }

                String nameTag="<name>"+name+"</name>\n";
                String iNameTag="<i-name>" + iName +"</i-name>\n";

                if(!(messageElement.getElementsByTagName("variable").getLength() > 0) && messageElement.getElementsByTagName("structure_ref").getLength() > 0){
                    String structureRef = messageElement.getElementsByTagName("structure_ref").item(0).getTextContent();
                    structureRefTag="<structure_ref>" + structureRef +"</structure_ref>\n";
                }

                if (messageElement.getElementsByTagName("variable").getLength() > 0) {
                    NodeList variableNodes = document.getElementsByTagName("variable");
                    Node variableNode = variableNodes.item(variableIndex++);
                    if (variableNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element variableElement = (Element) variableNode;
                        variable=getStringFromElement(variableElement)+"\n";
                    }
                }


                if (messageElement.getElementsByTagName("properties").getLength() > 0) {
                    NodeList variableNodes = document.getElementsByTagName("properties");
                    Node variableNode = variableNodes.item(propertiesIndex++);
                    if (variableNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element variableElement = (Element) variableNode;
                        property=getStringFromElement(variableElement)+"\n";
                    }
                }
                String finalMessageTag="</message>\n";
                String allMessage=beginMessageTag + version + nameTag + iNameTag
                        + structureRefTag + variable + property + finalMessageTag;

                //InternalModule internalModule = new InternalModule(i,allMessage);
                //internalModuleRepository.save(internalModule);

                messages.add(allMessage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }


    private static String getStringFromElement(Element element) {
        StringWriter writer = new StringWriter();
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(element), new StreamResult(writer));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

}

