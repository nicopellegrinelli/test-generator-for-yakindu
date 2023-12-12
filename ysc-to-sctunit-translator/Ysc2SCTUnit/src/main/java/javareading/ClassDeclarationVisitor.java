package javareading;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * The Class ClassDeclarationVisitor.
 */
public class ClassDeclarationVisitor extends VoidVisitorAdapter<Void> {
	
	/**
	 * Visit a class decalrations, adding "Simplified" at the end of the name.
	 *
	 * @param the class declaration
	 * @param arg none
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration node, Void arg) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, arg);
		// Changes class name only of the public class
		if (node.isPublic())
			node.setName(node.getNameAsString()+"Simplified");
	}
}
