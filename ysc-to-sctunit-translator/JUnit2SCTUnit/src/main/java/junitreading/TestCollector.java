package junitreading;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import sctwriting.TestCase;

public class TestCollector extends VoidVisitorAdapter<List<TestCase>> {
	@Override
	public void visit(MethodDeclaration md, List<TestCase> collector) {
		super.visit(md, collector);

		if (md.getBody().toString().contains(".enter")) {

			List<VariableDeclarationExpr> variableDeclarationList = new ArrayList<VariableDeclarationExpr>();
			VoidVisitor<List<VariableDeclarationExpr>> variableDeclarationListExprCollector = new VariableDeclarationExprCollector();
			variableDeclarationListExprCollector.visit(md, variableDeclarationList);

			for (VariableDeclarationExpr fieldAccess : variableDeclarationList) {
				System.out.println(fieldAccess.getChildNodes().toString());
			}

//			String state = "";
//			for (FieldAccessExpr fieldAccess : fieldAccessList) {
//				System.out.println(fieldAccess.toString());
//				if (!fieldAccess.getChildNodes().get(0).toString().contains(".State"))
//					state += fieldAccess.getChildNodes().get(0).toString();
//				else
//					state += "." + fieldAccess.getChildNodes().get(1).toString().toLowerCase().replace("_", ".");
//				if (state.contains("final"))
//					state = state.replace(".final.", "_final_");
//			}

			List<MethodCallExpr> methodCallList = new ArrayList<MethodCallExpr>();
			VoidVisitor<List<MethodCallExpr>> methodCallExprCollector = new MethodCallExprCollector();
			methodCallExprCollector.visit(md, methodCallList);

			TestCase testCase = new TestCase(md.getNameAsString());
			for (MethodCallExpr methodCall : methodCallList) {
				String methodName = methodCall.getNameAsString();
				if (methodName.equals("enter")) {
					testCase.addEnter();
					continue;
				}
				if (methodName.equals("exit")) {
					testCase.addExit();
					continue;
				}
				if (methodName.startsWith("raise")) {
					testCase.addEvent(methodName.replace("raise", "").toLowerCase());
					continue;
				}
				if (methodName.equals("assertTrue") || methodName.equals("assertFalse")) {
					boolean assertTrue = methodName.equals("assertTrue");
					String parameter = methodCall.getArgument(0).toString();
					if (parameter.contains(".isActive()")) {
						testCase.addIsActive(assertTrue);
						continue;
					}
					if (parameter.contains(".isFinal()")) {
						testCase.addIsFinal(assertTrue);
						continue;
					}
					// testCase.addAssertState(state, assertTrue);
				}
			}

			collector.add(testCase);
		}
	}
}
