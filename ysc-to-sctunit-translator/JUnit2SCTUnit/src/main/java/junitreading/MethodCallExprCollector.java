package junitreading;

import java.util.List;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodCallExprCollector extends VoidVisitorAdapter<List<MethodCallExpr>>  {
	@Override
	public void visit(MethodCallExpr node, List<MethodCallExpr> collector) {
		super.visit(node, collector);
		collector.add(node);
	}
}
