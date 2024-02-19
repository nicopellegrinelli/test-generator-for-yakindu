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
	private List<String> statesNames;
	
	/** The list containing all the full names of events in the statechart. */
	private List<String> eventsNames;
	
	/**
	 * Instantiates a new statechart.
	 *
	 * @param path the path of the .ysc file
	 * @throws ParserConfigurationException if a DocumentBuildercannot be created which satisfies the configuration requested.
	 * @throws SAXException if any parse errors occur.
	 * @throws IOException if any IO errors occur.
	 */
	public Statechart(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(path));
		
		this.statechartNode = document.getElementsByTagName("sgraph:Statechart").item(0);
		
		this.initStatechart();
	}
		
	/**
	 * Inits the statechart obtaining statechart name, states names end events names.
	 */
	private void initStatechart() {
		// Obtain the name of the statechart
		Node attribute = this.statechartNode.getAttributes().getNamedItem("name");
		this.statechartName = attribute.getNodeValue();
		
		// Initialize data structures
		this.statesNames = new ArrayList<String>();
		this.eventsNames = new ArrayList<String>();
		
		// Search the nodes representing the starting regions of the statechart
		NodeList nodeList = this.statechartNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("regions")) {
				// Start the visit of the subtree from the node representing the region
				this.visitNode(child, this.statesNames, this.eventsNames);
			}
		}
	}
	
	/**
	 * Visit a node, recursively visit all its element child nodes.
	 *
	 * @param node the node to visit
	 * @param statesNames the list of all the names of the states visited so far
	 * @param eventsNames the list of all the names of the events of the tranistions visited so far
	 */
	private void visitNode(Node node, List<String> statesNames, List<String> eventsNames) {
		// If the node is a region, it may contain a final state
		// else, it is a vertex and it may have outgoing transitions
		if (node.getNodeName().equals("regions")) {
			this.checkForFinalState(node, statesNames);
		}else {
			this.checkForTransitions(node, eventsNames);
			// A "normal" state is a node with name "vertices" and the attribute "xsi:type" equals to "sgraph:State",
			// for that kind of node, the name is of interest
			Node attribute = node.getAttributes().getNamedItem("xsi:type");
			if (attribute.getNodeValue().equals("sgraph:State")) {
				statesNames.add(this.getFullName(node, ""));
			}
		}
		// All child element nodes that are regions or vertices are visited
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && 
					(child.getNodeName().equals("regions") || child.getNodeName().equals("vertices")))
				visitNode(child, this.statesNames, this.eventsNames);
		}
	}

	/**
	 * Check if the node (region) contatins a final state.
	 *
	 * @param node the node representing the region
	 * @param statesNames the list of all the names of the states visited so far
	 */
	private void checkForFinalState(Node node, List<String> statesNames) {
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			// A final state is a node with name "vertices" and the attribute "xsi:type" equals to "sgraph:FinalState"
			if (child.getNodeName().equals("vertices")) {
				Node attribute = child.getAttributes().getNamedItem("xsi:type");
				if (attribute.getNodeValue().equals("sgraph:FinalState")) {
					statesNames.add(this.getFullName(node, "._final_"));
					return;
				}
			}
		}
	}
	
	/**
	 * Check if the node (state) contatins outgoing transitions.
	 *
	 * @param node the node representing the state
	 * @param eventsNames the list of all the names of the events of the transitions visited so far
	 */
	private void checkForTransitions(Node node, List<String> eventsNames) {
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			// The nodes of interest are outgoingTransitions with a non empty name
			if (child.getNodeName().equals("outgoingTransitions")) {
				Node attribute = child.getAttributes().getNamedItem("specification");
				if (attribute != null) {
					String name = attribute.getNodeValue();
					if (!(name.equals("") || eventsNames.contains(name))) {
						eventsNames.add(name);
					}					
				}
			}
		}
	}

	/**
	 * Gets the full name of the node recursively, going up in the DOM tree.
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
	
	/**
	 * Gets the statechart name.
	 *
	 * @return the statechart name
	 */
	public String getStatechartName() {
		return this.statechartName;
	}
	
	/**
	 * Gets all states names.
	 *
	 * @return the list containing all states names
	 */
	public List<String> getStatesNames() {
		return this.statesNames;
	}
	
	/**
	 * Gets all events names.
	 *
	 * @return the list containing all events names
	 */
	public List<String> getEventsNames() {
		return this.eventsNames;
	}
	
}
