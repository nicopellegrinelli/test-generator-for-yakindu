package junitreading;

import java.util.List;

import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableDeclarationExprCollector extends VoidVisitorAdapter<List<VariableDeclarationExpr>>{
	@Override
	public void visit(VariableDeclarationExpr node, List<VariableDeclarationExpr> collector) {
		super.visit(node, collector);
		collector.add(node);
	}
}
