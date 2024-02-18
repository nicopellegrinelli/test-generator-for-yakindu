package generators;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.ParseException;
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
	 * @param args the arguments, the first one is the worksapce path,
	 * the second one is the name of the workspace where the .ysc file is located, 
	 * the third one is the name of the workspace were the generated java code will be placed,
	 * the fourth one is the name of the file containning the statechart (NOT the name of the statechart) without the extension,
	 * the fifth one is "time" and is present only if the statechart deals with time events
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException Signals that an I/O exception has occurred
	 * @throws SAXException the SAX exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException, ParserConfigurationException, SAXException, ParseException {
		
		ParsedArgs parsedArgs = CLIManager.parse(args);
		
		
		// Collect all needed Strings 
		String workspacePath = args[0];
		String projectName = args[1];
		String packageName = args[2];
		String yscFileName = args[3];
		
		String projectPath = workspacePath + "\\" + projectName;
		String yscPath = projectPath + "\\model\\" + yscFileName + ".ysc";
		
		// Obtain the statechart name and the names of its states,
		// create a dictionary for the states names with the corresponding enum as key,
		// create a dictionary for the events names with the corresponding method as key
		System.out.println("*******************************************");
		System.out.println("Retrieving informations from the statechart...");
		System.out.println("*******************************************");
		Statechart statechart = new Statechart(yscPath);
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
		
		// Collect all needed Strings
		String itemisScc = "C:\\Users\\lenovo\\Desktop\\itemis_CREATE\\scc.bat";
		
		String classPath = projectPath + "\\src\\" + packageName + "\\" + statechartName + ".java";
		String simplifiedClassPath = projectPath + "\\src\\" + packageName + "\\" + statechartName + "Simplified.java";		
		
		String compilerD = "-d " + projectPath + "\\bin";
		String compilerClasspath = "-classpath " + projectPath + "\\src\\";
		
		String evoTarget = "-class " + packageName + "." + statechartName;
		String evoSimplifiedTarget = "-class " + packageName + "." + statechartName + "Simplified";
		String evoOptions = "-projectCP " + projectPath + "\\bin";
		String evoBaseDir = "-base_dir " + projectPath;
		
		String junitTestPath = projectPath + "\\evosuite-tests\\" + packageName + "\\" + statechartName + "_ESTest.java" ;
		String sctunitTestPath = projectPath + "\\model\\" + statechartName + "Test.sctunit" ;
		
		String simplifiedJunitTestPath = projectPath + "\\evosuite-tests\\" + packageName + "\\" + statechartName + "Simplified_ESTest.java" ;
		String simplifiedSctunitTestPath = projectPath + "\\model\\" + statechartName + "SimplifiedTest.sctunit" ;
		
		// Generate the .sgen file needed by Itemis Create to generate the java code
		boolean timeBased = args.length == 5 && args[4].equals("time");
		Generators.generateSgenJava(projectPath, statechartName, packageName, projectName, timeBased);
		
		// Call the Itemis Create generators
		Generators.callICGenerators(projectPath, itemisScc);
		
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
	    try {
	    	Generators.generateJunit(evoTarget, evoOptions, evoBaseDir);
	    } catch (SecurityException e) {
	    	Generators.generateSctunit(junitTestPath, sctunitTestPath,
	    			statechartName, statesNames, eventsNames);
	    }
	    try {
	    	Generators.generateJunit(evoSimplifiedTarget, evoOptions, evoBaseDir);
	    } catch (SecurityException e) { 
	    	Generators.generateSctunit(simplifiedJunitTestPath, simplifiedSctunitTestPath,
	    			statechartName, statesNames, eventsNames);
	    }
	    // Change the security manager back to the default one to let the execution ends
	    System.setSecurityManager(default_sm);
		System.out.println("*******************************************");
		System.out.println("Finished.");
		System.out.println("*******************************************");
	    System.exit(0);
	}

}
