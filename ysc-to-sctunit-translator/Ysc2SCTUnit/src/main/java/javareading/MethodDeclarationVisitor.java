package javareading;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * The Class MethodDeclarationVisitor.
 */
public class MethodDeclarationVisitor extends VoidVisitorAdapter<Void> {
	
	/**
	 * Visit a method decalration, set the visibility to private if it is protected.
	 *
	 * @param the method declaration
	 * @param arg none
	 */
	@Override
	public void visit(MethodDeclaration node, Void arg) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, arg);
		// Changes method visibility from protected to provate
		if (node.isProtected()) {
			node.setProtected(false);
			node.setPrivate(true);
		}
	}

}
