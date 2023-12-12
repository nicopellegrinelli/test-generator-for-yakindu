package junitreading;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * The Class ClassNameCollector.
 */
public class ClassNameCollector extends VoidVisitorAdapter<List<String>>{
	
	/**
	 * Visit the class declaration and add the name to the collector.
	 *
	 * @param node the class declaration
	 * @param collector the collector
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration node, List<String> collector) {
		super.visit(node, collector);
		collector.add(node.getNameAsString());
	}
}
