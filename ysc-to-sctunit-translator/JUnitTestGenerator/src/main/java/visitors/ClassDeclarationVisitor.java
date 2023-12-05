package visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassDeclarationVisitor extends VoidVisitorAdapter<Void> {
	@Override
	public void visit(ClassOrInterfaceDeclaration node, Void arg) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, arg);
		// Changes class name
		node.setName(node.getNameAsString()+"Simplified");
	}
}
