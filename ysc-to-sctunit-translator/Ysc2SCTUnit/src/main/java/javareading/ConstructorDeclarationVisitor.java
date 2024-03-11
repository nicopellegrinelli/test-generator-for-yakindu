package javareading;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * The Class ConstructorDeclarationVisitor.
 */
public class ConstructorDeclarationVisitor extends VoidVisitorAdapter<Void> {

	/**
	 * Visit a constructor declaration, add "Simplified" at the end of the name.
	 *
	 * @param the constructor declaration
	 * @param arg none
	 */
	@Override
	public void visit(ConstructorDeclaration node, Void arg) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, arg);
		// Changes constructor name if it has no parameters
		// Else, change the type of the parameter
		if (node.findAll(Parameter.class).isEmpty()) {
			node.setName(node.getNameAsString() + "Simplified");
		} else {
			// The constructor has one and only one parameter
			Parameter p = node.findAll(Parameter.class).get(0);
			p.setType(p.getTypeAsString() + "Simplified");
		}
	}
}
