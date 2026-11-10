package services;

import entities.Dependency;
import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NuspecParser {
    /**
     * @param xml данные о зависимостях
     * @return список зависимостей
     */
    public List<Dependency> parse(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        Document document = factory.newDocumentBuilder().parse(
                new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        List<Dependency> result = new ArrayList<>();
        NodeList dependenciesNodes = document.getElementsByTagNameNS("*", "dependencies");

        if (dependenciesNodes.getLength() == 0) {
            return result;
        }

        Element dependencies = (Element) dependenciesNodes.item(0);
        NodeList children = dependencies.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) { continue; }
            Element element = (Element) node;

            if (element.getLocalName().equals("dependency")) {
                addDependency(element, result);
            }

            if (element.getLocalName().equals("group")) {
                NodeList groupChildren = element.getChildNodes();
                for (int j = 0; j < groupChildren.getLength(); j++) {
                    Node child = groupChildren.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE &&
                        child.getLocalName().equals("dependency")) {
                        addDependency((Element) child, result);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Отображение зависимостей в виде списка
     */
    private void addDependency(Element element, List<Dependency> result) {
        String name = element.getAttribute("id");
        String version = element.getAttribute("version");
        result.add(new Dependency(name, version));
    }
}