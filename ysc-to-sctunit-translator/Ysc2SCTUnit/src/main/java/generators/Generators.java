package generators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import javareading.ClassDeclarationVisitor;
import javareading.ConstructorDeclarationVisitor;
import javareading.MethodDeclarationVisitor;
import junitreading.ClassNameCollector;
import junitreading.TestCaseCollector;
import testcase.TestCase;

/**
 * Contains a set of methods usable to generate various type of files.
 * All toghether, the methods can generate a .sctunit file starting from a .ysc file. 
 */
public final class Generators {
	
	/**
	 * Genarate the .sgen file needed by Itemis Create to generate java code from a .ysc file (a statechart).
	 *
	 * @param projectPath the path of the project
	 * @param statechartName the name of the statechart, it must be in a folder named model
	 * @param packageName the name of the package were the .java will be placed
	 * @param projectName the name of the project
	 * @param time true if the statechart deals with time events, false otherwise
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void genarateSgen(String projectPath, String statechartName, String packageName, String projectName, boolean time)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Generating .sgen file...");
		System.out.println("*******************************************");
		STGroupFile group = new STGroupFile(".\\template\\sgen_template.stg");
		ST st = group.getInstanceOf("generator");
		st.add("project_name", projectName);
		st.add("package_name", packageName);
		st.add("statechart_name", statechartName);
		if(time) st.add("time", "");
		File genFile = new File(projectPath + "\\model\\" + statechartName + ".sgen");
		st.write(genFile, null);
	}
	
	/**
	 * Call Itemis Create code generator to generate a .java file.
	 *
	 * @param projectPath the path of the project
	 * @param itemisScc the path of the scc.bat file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void genarateJava(String projectPath, String itemisScc)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Calling Itemis Create code generator...");
		System.out.println("*******************************************");
		ProcessBuilder pb = new ProcessBuilder();
		pb.redirectErrorStream(true);
		pb.directory(new File(projectPath));
		pb.command(itemisScc);
		Process p = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		System.out.println("");
		reader.close();
	}
	
	/**
	 * Genarate a simplified version of the java class present in the path passed as parameter.
	 *
	 * @param classPath the class path of the starting .java file
	 * @param simplifiedClassPath the class path of the .java file containing the simplified version
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void genarateSimplifiedJava(String classPath, String simplifiedClassPath)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Generating simplified java class...");
		System.out.println("*******************************************");
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(classPath));
		VoidVisitor<Void> classVisitor = new ClassDeclarationVisitor();
		VoidVisitor<Void> constructorVisitor = new ConstructorDeclarationVisitor();
		VoidVisitor<Void> methodVisitor = new MethodDeclarationVisitor();
		classVisitor.visit(cu, null);
		constructorVisitor.visit(cu, null);
		methodVisitor.visit(cu, null);
		BufferedWriter writer = new BufferedWriter(new FileWriter(simplifiedClassPath));
		writer.write(cu.toString());
		writer.close();
	}
	
	/**
	 * Call Evosuite to genarate a .junit file.
	 *
	 * @param evoTarget the string "-class ClassName", where ClassName is the fully qualified class name
	 * @param evoOptions the string "-projectCP ClassPath", where ClassPath is the class path for the test generation
	 * @param evoBaseDir the string "-base_dir Directory", where directory is the directory in which tests will be placed
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void genarateJunit(String evoTarget, String evoOptions, String evoBaseDir)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Calling Evosuite test generator...");
		System.out.println("*******************************************");
	    List<String> evoArgs = new ArrayList<>();
		evoArgs.addAll(Arrays.asList(evoTarget.split(" ")));
		evoArgs.addAll(Arrays.asList(evoOptions.split(" ")));
		evoArgs.addAll(Arrays.asList(evoBaseDir.split(" ")));
		//evoArgs.add("-Dsearch_budget=1");
		evoArgs.add("-generateSuite");
		org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));
	}
	
	/**
	 * Genarate a .sctunit file obtained translating a .java file containing junit tests.
	 *
	 * @param junitTestPath the path of the .java file containing the junit test cases
	 * @param sctunitTestPath the path of the generated .sctunit file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void genarateSctunit(String junitTestPath, String sctunitTestPath)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Generating .sctunit file...");
		System.out.println("*******************************************");
		// Get the compilation unit of the (test) class
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(junitTestPath));
		
		// Obtain the statechart name starting from the name of the test class
		List<String> classNameList = new ArrayList<String>();
		VoidVisitor<List<String>> classNameCollector = new ClassNameCollector();
		classNameCollector.visit(cu, classNameList);
		String statechartName = classNameList.get(0).replace("_ESTest", "").replace("Simplified", "");
		
		// Visit all the (test) methods contained in the compilation unit.
		// Each visit of a method produces a TestCase object 
		List<TestCase> testCaseList = new ArrayList<TestCase>();
		VoidVisitor<List<TestCase>> testCaseCollector = new TestCaseCollector();
		testCaseCollector.visit(cu, testCaseList);
		
		// Print to video the SCTUnit file
		STGroupFile group = new STGroupFile(".\\template\\sctunit_template.stg");
		ST st = group.getInstanceOf("test_class");
		st.add("statechart_name", statechartName);
		st.add("test_suite", testCaseList);
		File genFile = new File(sctunitTestPath);
		st.write(genFile, null);
	}
	

}
