package generators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cli.CLIManager;
import cli.ParsedArgs;
import statechart.Statechart;


/**
 * The Class TestGenerator.
 */
public class TestGenerator {
	/**
	 * The main method, generate a .sctunit file (test suite for a statechat)
	 * starting from a .ysc file (a statechart).
	 *
	 * @param args the command line arguments.
	 * @throws ParserConfigurationException if a DocumentBuildercannot while instantiating a Statechart object.
	 * @throws SAXException if there are any problems encountered while parsing the xml file representing the statechart.
	 * @throws IOException if any IO errors occur.
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		System.out.println("--------------------------------------------------------------");
		System.out.println("\t\t\tYsc2SCTUnit");
		System.out.println("--------------------------------------------------------------");

		// Parse the arguments
		ParsedArgs parsedArgs = CLIManager.parse(args);
		if (parsedArgs == null) {
			System.out.println("--------------------------------------------------------------");
			return;
		}
		String itemisScc = parsedArgs.getSccPath();
		String workspacePath = parsedArgs.getWorkspacePath();
		String projectName = parsedArgs.getProjectName();
		String targetDir = parsedArgs.getTargetDir();
		String targetPackage = parsedArgs.getTargetPackage();
		String dottedTargetPackage = targetPackage.replace("\\", ".");
		String sourceDir = parsedArgs.getSourceDir();
		String sourceFile = parsedArgs.getSourceFile();
		String binaryDir = parsedArgs.getBinaryDir();
		String evoTestDir = parsedArgs.getEvoTestDir();
		boolean hasSearchBudget = parsedArgs.hasSearchBudget();
		int evoSearchBudget = parsedArgs.getEvoSearchBudget();
		boolean timeService = parsedArgs.hasT();
		
		// Obtain the Strings needed to retrieve information from the statechart file
		String projectPath = workspacePath + "\\" + projectName;
		String sourceFilePath = projectPath + "\\" + sourceDir + "\\" + sourceFile;
		
		// Obtain the statechart name and the names of its states,
		// create a dictionary for the states names with the corresponding enum as key,
		// create a dictionary for the events names with the corresponding method as key
		System.out.println("*******************************************");
		System.out.println("Reading statechart file...");
		System.out.println("*******************************************");
		Statechart statechart = new Statechart(sourceFilePath);
		String statechartName = statechart.getStatechartName();
		Map<String, String> statesNames = new HashMap<String,String>();
		for(String name : statechart.getStatesNames()) {
			String enumName = name.toUpperCase().replace('.', '_');
			statesNames.put(enumName, name);
		}
		Map<String, String> eventsNames = new HashMap<String,String>();
		for(String name : statechart.getEventsNames()) {
			String methodName = "raise" + name.substring(0,1).toUpperCase() + name.substring(1);
			eventsNames.put(methodName, name);
		}
		
		// Obtain all needed Strings		
		String classPath = projectPath + "\\" + targetDir + "\\" + targetPackage + "\\" + statechartName + ".java";
		String simplifiedClassPath = projectPath + "\\" + targetDir + "\\" + targetPackage + "\\" + statechartName + "Simplified.java";		
		
		String compilerD = "-d " + projectPath + "\\" + binaryDir;
		String compilerClasspath = "-classpath " + projectPath + "\\" + targetDir;
		
//		String evoClass = "-class " + dottedTargetPackage + "." + statechartName;
		String evoSimplifiedClass = "-class " + dottedTargetPackage + "." + statechartName + "Simplified";
		String evoProjectCP = "-projectCP " + projectPath + "\\" + binaryDir;
		String evoDTestDir = "-Dtest_dir=" + projectPath + "\\" + evoTestDir;
		String evoDReportDir = "-Dreport_dir=" + projectPath + "\\evosuite-report";
		
//		String junitTestPath = projectPath + "\\" + evoTestDir + "\\" + targetPackage + "\\" + statechartName + "_ESTest.java" ;
//		String sctunitTestPath = projectPath + "\\" + sourceDir + "\\" + statechartName + "Test.sctunit" ;
		
		String simplifiedJunitTestPath = projectPath + "\\" + evoTestDir + "\\" + targetPackage + "\\" + statechartName + "Simplified_ESTest.java" ;
		String simplifiedSctunitTestPath = projectPath + "\\" + sourceDir + "\\" + statechartName + "SimplifiedTest.sctunit" ;
		
		// Generate the .sgen file needed by Itemis Create to generate the java code
		Generators.generateSgenJava(projectPath, projectName, statechartName, sourceDir,
				dottedTargetPackage, targetDir, timeService);
		
		// Call the Itemis Create generators
		Generators.callICGenerators(projectPath, itemisScc, sourceDir, sourceFile, statechartName);
		
		// Compile the generated classes
		compile(compilerD, compilerClasspath, classPath);		
		
		// Modify the generated Java code to create a simplified version
		Generators.generateSimplifiedJava(classPath, simplifiedClassPath);
		
		// Compile the new simplified class
		compile(compilerD, compilerClasspath, simplifiedClassPath);
		
//	    Generators.generateJunit(evoClass, evoProjectCP, evoDTestDir,
//	    		evoDReportDir, hasSearchBudget, evoSearchBudget);
//	    Generators.generateSctunit(junitTestPath, sctunitTestPath,
//	    		statechartName, statesNames, eventsNames);
		
		// Call the Evosuite test generator
	    Generators.generateJunit(evoSimplifiedClass, evoProjectCP, evoDTestDir,
	    		evoDReportDir, hasSearchBudget, evoSearchBudget);
	    
	    // Generate the .sctunit file
	    Generators.generateSctunit(simplifiedJunitTestPath, simplifiedSctunitTestPath,
	    		statechartName, statesNames, eventsNames);

		// End the execution
		System.out.println("*******************************************");
		System.out.println("Finished.");
		System.out.println("*******************************************");
		System.out.println("--------------------------------------------------------------");
		System.exit(0);
	}
	
	/**
	 * Compile.
	 *
	 * @param compilerD the string "-d ProjectPath", where ProjectPath is the path of the directory containing the .class files
	 * @param compilerClasspath the string "-classpath ClassPath", where ClassPath is the path of the directory containing the .java files
	 * @param classPath the path of the class to compile
	 */
	private static void compile(String compilerD, String compilerClasspath, String classPath){
		System.out.println("*******************************************");
		System.out.println("Compiling...");
		System.out.println("*******************************************");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<String> compilationArgs = new ArrayList<String>();
		compilationArgs.addAll(Arrays.asList(compilerD.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerClasspath.split(" ")));
		compilationArgs.add("-implicit:class");
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
		File f = new File(classPath);
        Iterable<? extends JavaFileObject> compilationUnits = stdFileManager.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
	}

}
