package generators;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cli.CLIManager;
import cli.ParsedArgs;
import statechart.Statechart;
import support.CompilerManager;
import support.MySecurityManager;

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
		
		String evoClass = "-class " + dottedTargetPackage + "." + statechartName;
		String evoSimplifiedClass = "-class " + dottedTargetPackage + "." + statechartName + "Simplified";
		String evoProjectCP = "-projectCP " + projectPath + "\\" + binaryDir;
		String evoDTestDir = "-Dtest_dir=" + projectPath + "\\" + evoTestDir;
		String evoDReportDir = "-Dreport_dir=" + projectPath + "\\evosuite-report";
		
		String junitTestPath = projectPath + "\\" + evoTestDir + "\\" + targetPackage + "\\" + statechartName + "_ESTest.java" ;
		String sctunitTestPath = projectPath + "\\" + sourceDir + "\\" + statechartName + "Test.sctunit" ;
		
		String simplifiedJunitTestPath = projectPath + "\\" + evoTestDir + "\\" + targetPackage + "\\" + statechartName + "Simplified_ESTest.java" ;
		String simplifiedSctunitTestPath = projectPath + "\\" + sourceDir + "\\" + statechartName + "SimplifiedTest.sctunit" ;
		
		// Generate the .sgen file needed by Itemis Create to generate the java code
		Generators.generateSgenJava(projectPath, projectName, statechartName, sourceDir,
				dottedTargetPackage, targetDir, timeService);
		
		// Call the Itemis Create generators
		Generators.callICGenerators(projectPath, itemisScc, sourceDir, sourceFile, statechartName);
		
		// Compile the generated classes
		CompilerManager.compile(compilerD, compilerClasspath, classPath);		
		
		// Modify the generated Java code to create a simplified version
		Generators.generateSimplifiedJava(classPath, simplifiedClassPath);
		
		// Compile the new simplified class
		CompilerManager.compile(compilerD, compilerClasspath, simplifiedClassPath);
		
		// Call the Evosuite test generator both on the original and the simplified versions
		// Change the security manager to avoid JVM stop running after Evosuite calls System.exit(0);
		SecurityManager default_sm = System.getSecurityManager();
		MySecurityManager my_sm = new MySecurityManager();
	    System.setSecurityManager(my_sm);
//	    try {
//	    	Generators.generateJunit(evoClass, evoProjectCP, evoDTestDir,
//	    			evoDReportDir, hasSearchBudget, evoSearchBudget);
//	    } catch (SecurityException e) {
//	    	Generators.generateSctunit(junitTestPath, sctunitTestPath,
//	    			statechartName, statesNames, eventsNames);
//	    }
	    try {
	    	Generators.generateJunit(evoSimplifiedClass, evoProjectCP, evoDTestDir,
	    			evoDReportDir, hasSearchBudget, evoSearchBudget);
	    } catch (SecurityException e) { 
	    	Generators.generateSctunit(simplifiedJunitTestPath, simplifiedSctunitTestPath,
	    			statechartName, statesNames, eventsNames);
	    }
	    // Change the security manager back to the default one to let the execution ends
	    System.setSecurityManager(default_sm);
		System.out.println("*******************************************");
		System.out.println("Finished.");
		System.out.println("*******************************************");
		System.out.println("--------------------------------------------------------------");
	    System.exit(0);
	}

}
