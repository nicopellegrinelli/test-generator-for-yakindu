package junitreading;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import sctwriting.TestCase;

public class JavaParserTest {

	public static void main(String[] args) throws FileNotFoundException {
		// Gets the compilation unit of the (test) class
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(".\\resources\\junit_class_example.txt"));
		
		// Obtains the statechart name starting from the name of the test class
		List<String> classNameList = new ArrayList<String>();
		VoidVisitor<List<String>> classCollector = new ClassDeclarationCollector();
		classCollector.visit(cu, classNameList);
		String statechartName = classNameList.get(0).replace("_ESTest", "");
		
		// Visits all the (test) methods contained in the compilation unit.
		// Each visit of a method produces a TestCase object 
		List<TestCase> testList = new ArrayList<TestCase>();
		VoidVisitor<List<TestCase>> methodCollector = new TestCollector();
		methodCollector.visit(cu, testList);
		
		// Prints to video the SCTUnit file
		STGroupFile group = new STGroupFile(".\\template\\sct_template.stg");
		ST st = group.getInstanceOf("test_class");
		st.add("statechart_name", statechartName);
		st.add("test_suite", testList);
		System.out.println(st.render());
	}

}
