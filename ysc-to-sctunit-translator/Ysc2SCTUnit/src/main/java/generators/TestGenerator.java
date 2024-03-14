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
	 * @throws ParserConfigurationException if a DocumentBuildercannot while
	 *                                      instantiating a Statechart object.
	 * @throws SAXException                 if there are any problems encountered
	 *                                      while parsing the xml file representing
	 *                                      the statechart.
	 * @throws IOException                  if any IO errors occur.
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
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

		// Obtain the statechart name and the names of its states, events and interfaces,
		// create a dictionary for the states names with the corresponding enum as key,
		// create a dictionary for the events names with the corresponding method as key,
		// create a dictionary for the interfaces names with the corresponding class name as key,
		System.out.println("*******************************************");
		System.out.println("Reading statechart file...");
		System.out.println("*******************************************");
		Statechart statechart = new Statechart(sourceFilePath);
		String statechartName = statechart.getStatechartName();
		Map<String, String> statesNames = new HashMap<String, String>();
		for (String name : statechart.getStatesNames()) {
			String enumName = name.toUpperCase().replace('.', '_');
			statesNames.put(enumName, name);
		}
		Map<String, String> eventsNames = new HashMap<String, String>();
		for (String name : statechart.getEventsNames()) {
			String methodName = "raise" + name.substring(0, 1).toUpperCase() + name.substring(1);
			eventsNames.put(methodName, name);
		}
		Map<String, String> interfacesNames = new HashMap<String, String>();
		for (String name : statechart.getInterfacesNames()) {
			String className = name.substring(0, 1).toUpperCase() + name.substring(1);
			interfacesNames.put(className, name);
		}

		// Obtain all needed Strings
		// In some cases, the statechart name with first letter capital is needed
		String firstUpperStatechartName = statechartName.substring(0, 1).toUpperCase() + statechartName.substring(1);

		String sgenPath = projectPath + "\\" + sourceDir + "\\" + firstUpperStatechartName + ".sgen";

		String javaPath = projectPath + "\\" + targetDir + "\\" + targetPackage + "\\" + firstUpperStatechartName
				+ ".java";
		String simplifiedJavaPath = projectPath + "\\" + targetDir + "\\" + targetPackage + "\\"
				+ firstUpperStatechartName + "Simplified.java";

		String compilerD = "-d " + projectPath + "\\" + binaryDir;
		String compilerClasspath = "-classpath " + projectPath + "\\" + targetDir;

		String evoClass = "-class " + dottedTargetPackage + "." + firstUpperStatechartName;
		String evoSimplifiedClass = "-class " + dottedTargetPackage + "." + firstUpperStatechartName + "Simplified";
		String evoProjectCP = "-projectCP " + projectPath + "\\" + binaryDir;
		String evoDTestDir = "-Dtest_dir=" + projectPath + "\\" + evoTestDir;
		String evoDReportDir = "-Dreport_dir=" + projectPath + "\\evosuite-report";

		String junitPath = projectPath + "\\" + evoTestDir + "\\" + targetPackage + "\\" + firstUpperStatechartName
				+ "_ESTest.java";
		String sctunitPath = projectPath + "\\" + sourceDir + "\\" + firstUpperStatechartName + "Test.sctunit";

		String simplifiedJunitPath = projectPath + "\\" + evoTestDir + "\\" + targetPackage + "\\"
				+ firstUpperStatechartName + "Simplified_ESTest.java";
		String simplifiedSctunitPath = projectPath + "\\" + sourceDir + "\\" + firstUpperStatechartName
				+ "SimplifiedTest.sctunit";

		// Generate the .sgen file needed by Itemis Create to generate the java code
		Generators.generateSgenJava(projectName, statechartName, sgenPath, targetDir, dottedTargetPackage, timeService);

		// Call the Itemis Create generators
		Generators.callICGenerators(projectPath, itemisScc, sourceDir, sourceFile, statechartName);

		// Compile the generated classes
		compile(compilerD, compilerClasspath, javaPath);

		// Modify the generated Java code to create a simplified version
		Generators.generateSimplifiedJava(javaPath, simplifiedJavaPath);

		// Compile the new simplified class
		compile(compilerD, compilerClasspath, simplifiedJavaPath);

		Generators.generateJunit(evoClass, evoProjectCP, evoDTestDir, evoDReportDir, hasSearchBudget, evoSearchBudget);
		Generators.generateSctunit(junitPath, sctunitPath, statechartName, statesNames, eventsNames, interfacesNames);

		// Call the Evosuite test generator
		Generators.generateJunit(evoSimplifiedClass, evoProjectCP, evoDTestDir, evoDReportDir, hasSearchBudget,
				evoSearchBudget);

		// Generate the .sctunit file
		Generators.generateSctunit(simplifiedJunitPath, simplifiedSctunitPath, statechartName, statesNames, eventsNames,
				interfacesNames);

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
	 * @param compilerD         the string "-d ProjectPath", where ProjectPath is
	 *                          the path of the directory containing the .class
	 *                          files
	 * @param compilerClasspath the string "-classpath ClassPath", where ClassPath
	 *                          is the path of the directory containing the .java
	 *                          files
	 * @param classPath         the path of the class to compile
	 */
	private static void compile(String compilerD, String compilerClasspath, String classPath) {
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
		Iterable<? extends JavaFileObject> compilationUnits = stdFileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
	}

}
