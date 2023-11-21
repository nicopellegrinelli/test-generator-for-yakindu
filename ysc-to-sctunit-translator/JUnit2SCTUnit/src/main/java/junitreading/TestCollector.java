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
			VoidVisitor<List<VariableDeclarationExpr>> fieldDeclarationCollector = 
					new VariableDeclarationExprCollection();
			fieldDeclarationCollector.visit(md, variableDeclarationList);
			
			List<MethodCallExpr> methodCallList = new ArrayList<MethodCallExpr>();
			VoidVisitor<List<MethodCallExpr>> methodCallExprCollector = 
					new MethodCallExprCollector();
			methodCallExprCollector.visit(md, methodCallList);
			
			System.out.println(md.getNameAsString());
			
			for(VariableDeclarationExpr variableDeclaration : variableDeclarationList){
				variableDeclaration.getChildNodes()
									.forEach(n->n.getChildNodes()
									.forEach(n1->System.out.println(n1.toString() + "-->" + n1.getClass().toString())));
			}
			
			
			TestCase testcase = new TestCase(md.getNameAsString());
			for(MethodCallExpr methodCall : methodCallList){
				String methodName = methodCall.getNameAsString();
				if (methodName.equals("enter"))
					continue;
				if (methodName.equals("exit"))
					testcase.addExit();
				if (methodName.startsWith("raise")) {
					testcase.addEvent(methodName.replace("raise", "").toLowerCase());
				}
			}

			collector.add(testcase);
		}
	}
}
