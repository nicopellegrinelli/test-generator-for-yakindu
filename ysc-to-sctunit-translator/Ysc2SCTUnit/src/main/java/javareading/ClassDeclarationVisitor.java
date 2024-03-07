package javareading;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * The Class ClassDeclarationVisitor.
 */
public class ClassDeclarationVisitor extends VoidVisitorAdapter<Void> {
	
	/**
	 * Visit a class decalration, adding "Simplified" at the end of the name.
	 *
	 * @param the class declaration
	 * @param arg none
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration node, Void arg) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, arg);
		// Changes class name only if the class is public and not static (nested)
		// else, change the type of the field named parent
		if (node.isPublic() && !node.isStatic()) {
			node.setName(node.getNameAsString()+"Simplified");
		}else {
			// Used VariableDeclarator instead of FieldDeclaration because it makes the type modification easier
			List<VariableDeclarator> fields = node.findAll(VariableDeclarator.class);
			for (VariableDeclarator field : fields) {
				if (field.getName().toString().equals("parent")) {
					field.setType(field.getTypeAsString() + "Simplified");
					break;
				}
			}
		}
	}
}
