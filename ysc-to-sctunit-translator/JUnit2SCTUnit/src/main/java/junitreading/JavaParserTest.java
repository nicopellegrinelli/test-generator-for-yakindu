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
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(".\\resources\\junit_class_example.txt"));
		
		List<TestCase> testList = new ArrayList<TestCase>();
		VoidVisitor<List<TestCase>> methodVisitor = new TestCollector();
		methodVisitor.visit(cu, testList);
		
		STGroupFile group = new STGroupFile(".\\template\\sct_template.stg");
		ST st = group.getInstanceOf("test_class");
		st.add("statechart_name", "LightSwitch");
		st.add("test_list", testList);
//		System.out.println(st.render());
	}

}
