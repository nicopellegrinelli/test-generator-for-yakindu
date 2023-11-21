package junitreading;

import java.util.List;

import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableDeclarationExprCollection extends VoidVisitorAdapter<List<VariableDeclarationExpr>>{
	@Override
	public void visit(VariableDeclarationExpr md, List<VariableDeclarationExpr> collector) {
		super.visit(md, collector);
		collector.add(md);
	}
}
