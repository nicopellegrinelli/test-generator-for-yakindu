package generators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import javareading.ClassDeclarationVisitor;
import javareading.ConstructorDeclarationVisitor;
import javareading.MethodDeclarationVisitor;
import junitreading.TestCaseCollector;
import testcase.TestCase;

/**
 * Contains a set of methods usable to generate various type of files.
 * All toghether, the methods can generate a .sctunit file starting from a .ysc file. 
 */
public final class Generators {
	
	/**
	 * Generate the .sgen file needed by Itemis Create to generate java code from a .ysc file (a statechart).
	 *
	 * @param projectPath the path of the project
	 * @param projectName the name of the project
	 * @param statechartName the name of the statechart
	 * @param sourceDir the directory where the statechart is located
	 * @param targetPackage the target package
	 * @param targetDir the directory containing the target package
	 * @param timeService true if the statechart deals with time events, false otherwise
	 * @throws IOException if any IO errors occur.
	 */
	public static void generateSgenJava(String projectPath, String projectName,
			String statechartName, String sourceDir, String targetPackage, String targetDir, boolean timeService)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Generating .sgen file...");
		System.out.println("*******************************************");
		STGroupFile group = new STGroupFile("sgen_java_template.stg");
		ST st = group.getInstanceOf("generator");
		// Put the generated file in projectPath\targetDir\targetPackage
		st.add("project_name", projectName);
		// For the directory, the.sgen file expect "\\" as separator instaed of "\"
		st.add("directory", targetDir.replace("\\", "\\\\"));
		st.add("package_name", targetPackage);
		st.add("statechart_name", statechartName);
		if(timeService) st.add("time", "");
		// Create the file in the same diractory of the statechart
		File genFile = new File(projectPath + "\\" + sourceDir +"\\" + statechartName + ".sgen");
		st.write(genFile, null);
	}
	
	/**
	 * Call Itemis Create code generators for each .sgen file in the specified project.
	 *
	 * @param projectPath the path of the project
	 * @param itemisScc the path of the scc.bat file
	 * @param sourceDir
	 * @param sourceFile
	 * @param statechartName
	 * @throws IOException if any IO errors occur.
	 */
	public static void callICGenerators(String projectPath, String itemisScc,
			String sourceDir, String sourceFile, String statechartName)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Calling Itemis Create code generator...");
		System.out.println("*******************************************");
		Process p = new ProcessBuilder(itemisScc, "-m", sourceFile + "," + statechartName + ".sgen")
				.redirectErrorStream(true)
				.directory(new File(projectPath + "\\" + sourceDir))
				.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (line.contains("done."))	break;
		}
		reader.close();
	}
	
	/**
	 * Generate a simplified version of the java class present in the path passed as parameter.
	 *
	 * @param classPath the class path of the starting .java file
	 * @param simplifiedClassPath the class path of the .java file containing the simplified version
	 * @throws IOException if any IO errors occur.
	 */
	public static void generateSimplifiedJava(String classPath, String simplifiedClassPath)
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
	 * Call Evosuite to generate a .junit file.
	 *
	 * @param evoClass the string "-class ClassName", where ClassName is the fully qualified class name
	 * @param evoProjectCP the string "-projectCP ClassPath", where ClassPath is the class path for the test generation
	 * @param DtestDir the string "Dtest_dir=Directory", where Directory is the directory in which tests will be placed
	 * @param DreportDir the string "Dreport_dir=Directory", where Directory is the directory in which statistics will be placed
	 * @param hasSearchBudget true if a search budget must be imposed, false otherwise
	 * @param searchBudget the search budget, not setted if hasSearchBudget is false 
	 */
	public static void generateJunit(String evoClass, String evoProjectCP,
			String DtestDir, String DreportDir, boolean hasSearchBudget, int searchBudget) {
		System.out.println("*******************************************");
		System.out.println("Calling Evosuite test generator...");
		System.out.println("*******************************************");
	    List<String> evoArgs = new ArrayList<>();
		evoArgs.addAll(Arrays.asList(evoClass.split(" ")));
		evoArgs.addAll(Arrays.asList(evoProjectCP.split(" ")));
		evoArgs.add(DtestDir);
		evoArgs.add(DreportDir);
		evoArgs.add("-generateSuite");
		if (hasSearchBudget)
			evoArgs.add("-Dsearch_budget=" + searchBudget);
		try {
			// This option is necessary for the jar to execute without warnings
			String jarRunningDir = new File(Generators.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getParentFile()
					.getPath();
			String evosuiteCP = "-evosuiteCP " + jarRunningDir	+ "\\libs\\evosuite-1.0.6.jar";
			evoArgs.addAll(Arrays.asList(evosuiteCP.split(" ")));
		} catch (URISyntaxException e) {
			System.out.println("An unexpected error occurred: some warnings may be generated,"
					+ " but the process should finish succesfully.");
		}
		org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));
	}
	
	/**
	 * Generate a .sctunit file obtained translating a .java file containing junit tests.
	 *
	 * @param junitTestPath the path of the .java file containing the junit test cases
	 * @param sctunitTestPath the path of the generated .sctunit file
	 * @param statechartName the  names of the statechart
	 * @param statesNames the dictionary of the states names with the corresponding enum as key
	 * @param eventsNames the dictionary of the events names with the corresponding method as key
	 * @throws IOException if any IO errors occur.
	 */
	public static void generateSctunit(String junitTestPath, String sctunitTestPath, 
			String statechartName, Map<String,String> statesNames, Map<String,String> eventsNames)
			throws IOException {
		System.out.println("*******************************************");
		System.out.println("Generating .sctunit file...");
		System.out.println("*******************************************");
		// Get the compilation unit of the (test) class
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(junitTestPath));
		
		// Visit all the (test) methods contained in the compilation unit.
		// Each visit of a method produces a TestCase object 
		List<TestCase> testCaseList = new ArrayList<TestCase>();
		VoidVisitor<List<TestCase>> testCaseCollector = new TestCaseCollector(statechartName, statesNames, eventsNames);
		testCaseCollector.visit(cu, testCaseList);
		
		// Print to video the SCTUnit file
		STGroupFile group = new STGroupFile("sctunit_template.stg");
		ST st = group.getInstanceOf("test_class");
		st.add("statechart_name", statechartName);
		st.add("test_suite", testCaseList);
		File genFile = new File(sctunitTestPath);
		st.write(genFile, null);
	}

}
