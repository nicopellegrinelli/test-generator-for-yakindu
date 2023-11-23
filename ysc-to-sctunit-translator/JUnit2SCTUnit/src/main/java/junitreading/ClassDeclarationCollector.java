package junitreading;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassDeclarationCollector extends VoidVisitorAdapter<List<String>>{
	@Override
	public void visit(ClassOrInterfaceDeclaration md, List<String> collector) {
		super.visit(md, collector);
		collector.add(md.getNameAsString());
	}
}
