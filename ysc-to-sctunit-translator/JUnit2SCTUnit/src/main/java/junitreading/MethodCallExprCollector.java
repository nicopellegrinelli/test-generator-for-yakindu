package junitreading;

import java.util.List;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodCallExprCollector extends VoidVisitorAdapter<List<MethodCallExpr>>  {
	@Override
	public void visit(MethodCallExpr md, List<MethodCallExpr> collector) {
		super.visit(md, collector);
		collector.add(md);
	}
}
