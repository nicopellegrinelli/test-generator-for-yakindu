package junitreading;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassDeclarationCollector extends VoidVisitorAdapter<List<String>>{
	@Override
	public void visit(ClassOrInterfaceDeclaration node, List<String> collector) {
		super.visit(node, collector);
		collector.add(node.getNameAsString());
	}
}
