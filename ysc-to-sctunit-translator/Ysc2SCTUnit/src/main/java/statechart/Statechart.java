package statechart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The Class Statechart.
 */
public class Statechart {	
	
	/** The node in the DOM representing the statechart. */
	private Node statechartNode;
	
	/** The statechart name. */
	private String statechartName;
	
	/** The list containing all the full names of states in the statechart. */
	private List<String> statesName;
	
	/**
	 * Instantiates a new statechart.
	 *
	 * @param path the path of the .ysc file
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Statechart(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(path));
		
		this.statechartNode = document.getElementsByTagName("sgraph:Statechart").item(0);
		
		this.initStatechartName();
		this.initStatesName();
	}
	
	/**
	 * Gets the statechart name.
	 *
	 * @return the statechart name
	 */
	public String getStatechartName() {
		return this.statechartName;
	}
	
	/**
	 * Gets the full states name.
	 *
	 * @return the list containing all states name
	 */
	public List<String> getStatesName() {
		return this.statesName;
	}
	
	/**
	 * Inits the statechart name.
	 */
	private void initStatechartName() {
		Node attribute = this.statechartNode.getAttributes().getNamedItem("name");
		this.statechartName = attribute.getNodeValue();
	}
	
	/**
	 * Inits the states name.
	 */
	private void initStatesName() {
		// Search the node representing the starting region of the statechart
		NodeList nodeList = this.statechartNode.getChildNodes();
		Node firstNode = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("regions")) {
				firstNode = child;
				break;
			}
		}
		// Start the visit of the subtree starting from the one representign the first region
		this.statesName = new ArrayList<String>();
		this.visitNode(firstNode, this.statesName);
	}
	
	/**
	 * Visit a node, recursively visit all its element child nodes.
	 *
	 * @param node the node to visit
	 * @param names the list of all the names of the states visited so far
	 */
	private void visitNode(Node node, List<String> names) {
		// If the node is a region, it may contatin a final state
		// else, its full name is added to the states name list
		if (node.getNodeName().equals("regions"))
			this.checkForFinalState(node, names);
		else
			names.add(this.getFullName(node, ""));
		// All child element nodes with the "name" attribute are visited
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getAttributes().getNamedItem("name") != null)
				visitNode(child, this.statesName);
		}
	}
	
	/**
	 * Check if the node (region) contatins a final state.
	 *
	 * @param node the node representing the region
	 * @param names the list of all the names of the states visited so far
	 */
	private void checkForFinalState(Node node, List<String> names) {
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			// A final state is represented by a node with name "vertices" and the attribute "xsi:type" equals to "sgraph:FinalState"
			if (child.getNodeName().equals("vertices")) {
				Node attribute = child.getAttributes().getNamedItem("xsi:type");
				if (attribute.getNodeValue().equals("sgraph:FinalState")) {
					names.add(this.getFullName(node, "._final_"));
					return;
				}
			}
		}
	}

	/**
	 * Gets the full name of the mode recursively, going up in the DOM tree.
	 *
	 * @param node the node for which it must be obtained the full name
	 * @param name the full name obtained before the call of this method, it must contain a dot at the start if it is not the first call
	 * @return the full name obtained at the end of the call of this method
	 */
	private String getFullName(Node node, String name) {
		// Note that non alphanumeric characters must be substitued with '_' to be compliant with SCTUnit
		String newName = node.getAttributes().getNamedItem("name").getNodeValue().replaceAll("[^a-zA-Z0-9]", "_")
						 + name;
		Node parent = node.getParentNode();
		if (parent.getNodeName().equals("sgraph:Statechart"))
			// If the "root" of the statechart is reached, the final full name is returned
			return newName;
		else	
			// The name of the parent node must be added (recursively) to the full name
			return this.getFullName(parent, "." + newName);
	}
	
}
