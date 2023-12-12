package junitreading;

import java.util.List;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * The Class MethodCallExprCollector.
 */
public class MethodCallExprCollector extends VoidVisitorAdapter<List<MethodCallExpr>>  {
	
	/**
	 * Visit the method call expression and add it to the collector.
	 *
	 * @param node method call expression
	 * @param collector the collector
	 */
	@Override
	public void visit(MethodCallExpr node, List<MethodCallExpr> collector) {
		super.visit(node, collector);
		collector.add(node);
	}
}
