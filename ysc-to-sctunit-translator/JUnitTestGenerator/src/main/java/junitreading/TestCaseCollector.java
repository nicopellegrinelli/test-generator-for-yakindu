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

import sctunitwriting.TestCase;

public class TestCaseCollector extends VoidVisitorAdapter<List<TestCase>> {
	@Override
	public void visit(MethodDeclaration node, List<TestCase> collector) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, collector);
		
		// Discards methods in which the method .enter IS NOT called or the method .setIsExecuting IS called
		if (!node.getBody().toString().contains(".enter") || node.getBody().toString().contains(".setIsExecuting")) {
			return;
		}
		
		// Gets all variable declarations expressions contained in the method
		List<VariableDeclarationExpr> variableDeclarationList = new ArrayList<VariableDeclarationExpr>();
		VoidVisitor<List<VariableDeclarationExpr>> variableDeclarationExprCollector = new VariableDeclarationExprCollector();
		variableDeclarationExprCollector.visit(node, variableDeclarationList);
		
		// Produces a set of pair of Strings for each variable declaration representing the associated assignment.
		// The first String is the variable name (left-hand-side of the assignment).
		// The second String is the assigned expression (right-hand-side of the assignment).
		Map<String, String> variableAssignments = new HashMap<String, String>();
		for (VariableDeclarationExpr variableDecl : variableDeclarationList) {
			List<Node> assignmentExpr = variableDecl.getChildNodes().get(0).getChildNodes(); 
			variableAssignments.put(assignmentExpr.get(1).toString(), assignmentExpr.get(2).toString());
		}
		
		// Gets all method call expressions contained in the method
		List<MethodCallExpr> methodCallList = new ArrayList<MethodCallExpr>();
		VoidVisitor<List<MethodCallExpr>> methodCallExprCollector = new MethodCallExprCollector();
		methodCallExprCollector.visit(node, methodCallList);
		
		// Creates and populate an instance of TestCase, adding an Action 
		// for each method call expression of interest contained in the test method.
		TestCase testCase = new TestCase(node.getNameAsString());
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
				testCase.addTriggerWithoutEvent();
				continue;
			}
			if (methodName.equals("assertTrue") || methodName.equals("assertFalse")) {
				boolean assertTrue = methodName.equals("assertTrue");
				// The method has an argument, that is retrieved.
				// If the argument is a variable, its assigned expression (right-hand-side of the assignment) is retrieved.
				String argument = methodCall.getArgument(0).toString();
				String variableAssignedExpr = variableAssignments.get(argument); // null if the argument is not a variable
				if (argument.contains(".isActive()")  
						|| (variableAssignedExpr != null && variableAssignedExpr.contains(".isActive()"))) {
					testCase.addIsActive(assertTrue);
					continue;
				}
				if (argument.contains(".isFinal()")
						|| (variableAssignedExpr != null && variableAssignedExpr.contains(".isFinal()"))) {
					testCase.addIsFinal(assertTrue);
					continue;
				}
				if(variableAssignedExpr != null && variableAssignedExpr.contains(".isStateActive")) {
					String stateName = variableAssignments.get(
							variableAssignedExpr.substring(variableAssignedExpr.indexOf('(')+1, variableAssignedExpr.indexOf(')')));
					stateName = stateName.replace("State.", "");
					int separator = stateName.lastIndexOf('.');
					stateName = stateName.substring(0, separator) + stateName.substring(separator).toLowerCase().replace("_", ".");
					if (stateName.contains("final"))
						stateName = stateName.replace(".final.", "_final_");
					testCase.addAssertState(stateName.replace("Simplified", ""), assertTrue);
				}
			}
		}
		// Returns the testCase as the result of the visit of the method 
		collector.add(testCase);
	}
}
