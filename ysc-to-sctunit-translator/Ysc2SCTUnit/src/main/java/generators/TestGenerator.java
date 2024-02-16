package generators;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	 * the fourth one is the statechart name,
	 * the fifth one is "time" and is present only if the statechart deals with time events
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException Signals that an I/O exception has occurred
	 * @throws SAXException the SAX exception
	 * @throws ParserConfigurationException the parser configuration exception
	 */
	public static void main(String[] args) throws InterruptedException, IOException, ParserConfigurationException, SAXException {
		// Collect all needed Strings 
		String workspacePath = args[0];
		String projectName = args[1];
		String packageName = args[2];
		String yscFileName = args[3];
		
		String projectPath = workspacePath + "\\" + projectName;
		String classPath = projectPath + "\\src\\" + packageName + "\\" + yscFileName + ".java";
		String simplifiedClassPath = projectPath + "\\src\\" + packageName + "\\" + yscFileName + "Simplified.java";
		
		String itemisScc = "C:\\Users\\lenovo\\Desktop\\itemis_CREATE\\scc.bat";
		
		String yscPath = projectPath + "\\model\\" + yscFileName + ".ysc";
		
		String compilerD = "-d " + projectPath + "\\bin";
		String compilerClasspath = "-classpath " + projectPath + "\\src\\";
		
		String evoTarget = "-class " + packageName + "." + yscFileName;
		String evoSimplifiedTarget = "-class " + packageName + "." + yscFileName + "Simplified";
		String evoOptions = "-projectCP " + projectPath + "\\bin";
		String evoBaseDir = "-base_dir " + projectPath;
		
		String junitTestPath = projectPath + "\\evosuite-tests\\" + packageName + "\\" + yscFileName + "_ESTest.java" ;
		String sctunitTestPath = projectPath + "\\model\\" + yscFileName + "Test.sctunit" ;
		
		String simplifiedJunitTestPath = projectPath + "\\evosuite-tests\\" + packageName + "\\" + yscFileName + "Simplified_ESTest.java" ;
		String simplifiedSctunitTestPath = projectPath + "\\model\\" + yscFileName + "SimplifiedTest.sctunit" ;
		
		// Obtain the statechart name and the names of its states,
		// create a dictionary for the state names with the corresponding enum as key
		System.out.println("*******************************************");
		System.out.println("Obtaining usable names for the states...");
		System.out.println("*******************************************");
		Statechart statechart = new Statechart(yscPath);
		String statecartName = statechart.getStatechartName();
		Map<String, String> statesName = new HashMap<String,String>();
		for(String name : statechart.getStatesName()) {
			String enumName = name.toUpperCase().replace('.', '_');
			statesName.put(enumName, name);
		}
		
		// Generate the .sgen file needed by Itemis Create to generate the java code
		boolean timeBased = args.length == 5 && args[4].equals("time");
		Generators.generateSgenJava(projectPath, statecartName, packageName, projectName, timeBased);
		
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
	    	Generators.generateSctunit(junitTestPath, sctunitTestPath, statecartName, statesName);
	    }
	    try {
	    	Generators.generateJunit(evoSimplifiedTarget, evoOptions, evoBaseDir);
	    } catch (SecurityException e) { 
	    	Generators.generateSctunit(simplifiedJunitTestPath, simplifiedSctunitTestPath, statecartName, statesName);
	    }
	    // Change the security manager back to the default one to let the execution ends
	    System.setSecurityManager(default_sm);
		System.out.println("*******************************************");
		System.out.println("Finished.");
		System.out.println("*******************************************");
	    System.exit(0);
	}

}
