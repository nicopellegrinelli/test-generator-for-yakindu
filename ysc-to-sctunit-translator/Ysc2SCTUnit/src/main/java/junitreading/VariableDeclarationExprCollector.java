package junitreading;

import java.util.List;

import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * The Class VariableDeclarationExprCollector.
 */
public class VariableDeclarationExprCollector extends VoidVisitorAdapter<List<VariableDeclarationExpr>>{
	
	/**
	 *  Visit the variable declaration expression and add it to the collector.
	 *
	 * @param node variable declaration expression
	 * @param collector the collector
	 */
	@Override
	public void visit(VariableDeclarationExpr node, List<VariableDeclarationExpr> collector) {
		super.visit(node, collector);
		collector.add(node);
	}
}
