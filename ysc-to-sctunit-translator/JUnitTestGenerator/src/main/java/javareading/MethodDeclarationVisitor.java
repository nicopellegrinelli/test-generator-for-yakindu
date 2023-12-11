package javareading;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodDeclarationVisitor extends VoidVisitorAdapter<Void> {
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
