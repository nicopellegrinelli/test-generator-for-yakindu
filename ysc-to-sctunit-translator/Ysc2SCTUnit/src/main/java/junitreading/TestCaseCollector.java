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

import testcase.TestCase;

/**
 * The Class TestCaseCollector.
 */
public class TestCaseCollector extends VoidVisitorAdapter<List<TestCase>> {
	
	/** The name of the statechart */
	String statechartName;
	
	/** The dictionary containing all the full names of states in the statechart.
	 *  The key is the string representing the corresponding enum */
	Map<String, String> statesNames;
	
	/** The dictionary containing all the names of events in the statechart.
	 *  The key is the string representing the corresponding method */
	Map<String, String> eventsNames;

	/**
	 * Instantiates a new TestCaseCollector regarding to a statechart.
	 *
	 * @param statechartName the name of the statechart
	 * @param statesName the disctionary of the states names with the corresponding enum as key
	 * @param eventsNames the dictionary of the events names with the corresponding method as key
	 */
	public TestCaseCollector(String statechartName, Map<String, String> statesNames, Map<String,String> eventsNames) {
		this.statechartName = statechartName;
		this.statesNames = statesNames;
		this.eventsNames = eventsNames;
	}
	
	/**
	 * Visit the method declaration and add a new TestCase instance representing the visited method declaration to the collector.
	 *
	 * @param node the method declaration
	 * @param collector the collector
	 */
	@Override
	public void visit(MethodDeclaration node, List<TestCase> collector) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, collector);
		
		// Discards methods in which the method .enter IS NOT called or the method .setIsExecuting IS called
//		if (!node.getBody().toString().contains(".enter") || node.getBody().toString().contains(".setIsExecuting")) {
//			return;
//		}
		
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
				// Ignores events not in the dictionary
				if (eventsNames.containsKey(methodName)) {
					testCase.addEvent(eventsNames.get(methodName));
				}else {
					System.out.println(node.getNameAsString() + ": problems encountered in the translation, the test may fail.");
				}
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
				String assertArgument = methodCall.getArgument(0).toString();
				// The statement that must be checked if it is true (or false) is retrieved.
				// If the argument is a known variable, its assigned expression (right-hand-side of the assignment) is used,
				// else, dircetly the argument is used.
				String statementToCheck;
				if(variableAssignments.containsKey(assertArgument)) {
					statementToCheck = variableAssignments.get(assertArgument);
				} else {
					statementToCheck = assertArgument;
				}
				if (statementToCheck.contains(".isActive()")) {
					testCase.addIsActive(assertTrue);
					continue;
				}
				if (statementToCheck.contains(".isFinal()")) {
					testCase.addIsFinal(assertTrue);
					continue;
				}
				if(statementToCheck.contains(".isStateActive")) {
					// The method has an argument, that is retrieved.
					String isStateActiveArgument = 
							statementToCheck.substring(statementToCheck.indexOf('(')+1, statementToCheck.lastIndexOf(')'));
					// The string representing the name of the state in Java is retrieved.
					// If the argument is a known variable, its assigned expression (right-hand-side of the assignment) is used,
					// else, dircetly the argument is used.
					String javaStateName;
					if(variableAssignments.containsKey(isStateActiveArgument)) {
						javaStateName = variableAssignments.get(isStateActiveArgument);
					} else {
						javaStateName = isStateActiveArgument;
					}
					// If the stateName is obtained using valueof, only the argument of valueof must be used as state name
					if (javaStateName.contains("valueof")) {
						javaStateName = javaStateName.substring(javaStateName.indexOf('(')+1, javaStateName.indexOf(')'));
					}
					// The string representing the enum is obtained
					javaStateName = javaStateName.substring(javaStateName.lastIndexOf('.')+1);
					// The nullstate has no equivalent in SCTUnit
					if (javaStateName.contains("$nullstate$"))
						continue;
					// Ignores states not in the dictionary
					if (statesNames.containsKey(javaStateName)) {
						// The string representing the name of the state in SCTUnit is retrieved.
						String sctunitStateName = statechartName + "." + statesNames.get(javaStateName);
						testCase.addAssertState(sctunitStateName, assertTrue);
					}else {
						System.out.println(node.getNameAsString() + ": problems encountered in the translation, the test may fail.");
					}
					continue;
				}
			}
		}
		// Returns the testCase as the result of the visit of the method 
		collector.add(testCase);
	}
}
