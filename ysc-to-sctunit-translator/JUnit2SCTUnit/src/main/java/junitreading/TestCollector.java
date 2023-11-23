package junitreading;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import sctwriting.TestCase;

public class TestCollector extends VoidVisitorAdapter<List<TestCase>> {
	@Override
	public void visit(MethodDeclaration md, List<TestCase> collector) {
		super.visit(md, collector);
		
		if (md.getBody().toString().contains(".enter")) {

			List<FieldAccessExpr> fieldAccessList = new ArrayList<FieldAccessExpr>();
			VoidVisitor<List<FieldAccessExpr>> fieldAccessExprCollector = new FieldAccessExprCollector();
			fieldAccessExprCollector.visit(md, fieldAccessList);

			String state = "";
			boolean finalState = false;
			for (FieldAccessExpr fieldAccess : fieldAccessList) {
				if (!fieldAccess.getChildNodes().get(0).toString().contains(".State"))
					state += fieldAccess.getChildNodes().get(0).toString();
				else
					state += "." + fieldAccess.getChildNodes().get(1).toString().toLowerCase().replace("_", ".");
			}
			finalState = state.contains("final");

			List<MethodCallExpr> methodCallList = new ArrayList<MethodCallExpr>();
			VoidVisitor<List<MethodCallExpr>> methodCallExprCollector = new MethodCallExprCollector();
			methodCallExprCollector.visit(md, methodCallList);

			TestCase testCase = new TestCase(md.getNameAsString());
			for (MethodCallExpr methodCall : methodCallList) {
				String methodName = methodCall.getNameAsString();
				if (methodName.equals("enter"))
					continue;
				if (methodName.equals("exit")) {
					testCase.addExit();
					continue;
				}
				if (methodName.startsWith("raise")) {
					testCase.addEvent(methodName.replace("raise", "").toLowerCase());
					continue;
				}
				if (!state.isEmpty() && !finalState) {
					if (methodName.equals("assertTrue")) {
						testCase.addAssertState(state, true);
						continue;
					}
					if (methodName.equals("assertFalse")) {
						testCase.addAssertState(state, false);
						continue;
					}
				}
			}

			collector.add(testCase);
		}
	}
}
