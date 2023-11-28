package junitreading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.Node;
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
		
		if (md.getBody().toString().contains(".enter") && !md.getBody().toString().contains(".setIsExecuting")) {

			List<VariableDeclarationExpr> variableDeclarationList = new ArrayList<VariableDeclarationExpr>();
			VoidVisitor<List<VariableDeclarationExpr>> variableDeclarationListExprCollector = new VariableDeclarationExprCollector();
			variableDeclarationListExprCollector.visit(md, variableDeclarationList);
			
			Map<String, String> variables = new HashMap<String, String>();
			
			for (VariableDeclarationExpr variableDecl : variableDeclarationList) {
				List<Node> n = variableDecl.getChildNodes().get(0).getChildNodes();
				variables.put(n.get(1).toString(), n.get(2).toString());
			}

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
				if (methodName.equals("runCycle")) {
					testCase.addProceedCycle();
					continue;
				}
				if (methodName.equals("triggerWithoutEvent")) {
					testCase.addTryggerWithoutEvent();
					continue;
				}
				if (methodName.equals("assertTrue") || methodName.equals("assertFalse")) {
					boolean assertTrue = methodName.equals("assertTrue");
					String parameterName = methodCall.getArgument(0).toString();
					String parameterValue = variables.get(parameterName);
					if (parameterName.contains(".isActive()")  
							|| (parameterValue != null && parameterValue.contains(".isActive()"))) {
						testCase.addIsActive(assertTrue);
						continue;
					}
					if (parameterName.contains(".isFinal()")
							|| (parameterValue != null && parameterValue.contains(".isFinal()"))) {
						testCase.addIsFinal(assertTrue);
						continue;
					}
					if(parameterValue != null && parameterValue.contains(".isStateActive")) {
						String stateName = variables.get(parameterValue.substring(parameterValue.indexOf('(')+1, parameterValue.indexOf(')')));
						stateName = stateName.replace("State.", "");
						int separator = stateName.indexOf('.');
						stateName = stateName.substring(0, separator) + stateName.substring(separator).toLowerCase().replace("_", ".");
						if (stateName.contains("final"))
							stateName = stateName.replace(".final.", "_final_");
						testCase.addAssertState(stateName, assertTrue);
					}
				}
			}

			collector.add(testCase);
		}
	}
}
