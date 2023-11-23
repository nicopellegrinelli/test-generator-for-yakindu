package junitreading;

import java.util.List;

import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldAccessExprCollector extends VoidVisitorAdapter<List<FieldAccessExpr>>{
	@Override
	public void visit(FieldAccessExpr md, List<FieldAccessExpr> collector) {
		super.visit(md, collector);
		collector.add(md);
	}
}
