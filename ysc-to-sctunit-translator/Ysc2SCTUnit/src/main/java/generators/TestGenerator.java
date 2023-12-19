package generators;

import java.io.IOException;

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
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// Collect all needed Strings 
		String workspacePath = args[0];
		String projectName = args[1];
		String packageName = args[2];
		String statechartName = args[3];
		
		String projectPath = workspacePath + "\\" + projectName;
		String classPath = projectPath + "\\src\\" + packageName + "\\" + statechartName + ".java";
		String simplifiedClassPath = projectPath + "\\src\\" + packageName + "\\" + statechartName + "Simplified.java";
		
		String itemisScc = "C:\\Program Files (x86)\\itemis_CREATE\\scc.bat";
		
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
		
		// Generate the .sgen file needed by Itemis Create
		boolean timeBased = args.length == 5 && args[4].equals("time");
		Generators.genarateSgen(projectPath, statechartName, packageName, projectName, timeBased);
		
		// Call the Itemis Create Java code generator
		Generators.genarateJava(projectPath, itemisScc);
		
		// Compile the generated classes
		CompilerManager.compile(compilerD, compilerClasspath, classPath);		
		
		// Modify the generated Java code to create a simplified version
		Generators.genarateSimplifiedJava(classPath, simplifiedClassPath);
		
		// Compile the new simplified class
		CompilerManager.compile(compilerD, compilerClasspath, simplifiedClassPath);	
		
		// Call the Evosuite test generator both on the original and the simplified versions
		// Change the security manager to avoid JVM stop running after Evosuite calls System.exit(0);
		SecurityManager default_sm = System.getSecurityManager();
		for (int i = 0; i < 20; i++) {
			sctunitTestPath = projectPath + "\\model\\" + statechartName + "Test" + Integer.toString(i) + ".sctunit" ;
			simplifiedSctunitTestPath = projectPath + "\\model\\" + statechartName + "SimplifiedTest" + Integer.toString(i) + ".sctunit" ;
			MySecurityManager my_sm = new MySecurityManager();
		    System.setSecurityManager(my_sm);
		    try {
		    	Generators.genarateJunit(evoTarget, evoOptions, evoBaseDir);
		    } catch (SecurityException e) {
		    	Generators.genarateSctunit(junitTestPath, sctunitTestPath);
		    }
	
		    try {
		    	Generators.genarateJunit(evoSimplifiedTarget, evoOptions, evoBaseDir);
		    } catch (SecurityException e) { 
		    	Generators.genarateSctunit(simplifiedJunitTestPath, simplifiedSctunitTestPath);
		    }
		}
	    // Change the security manager back to the default one to let the execution ends
	    System.setSecurityManager(default_sm);
		System.out.println("*******************************************");
		System.out.println("Finished.");
		System.out.println("*******************************************");
	    System.exit(0);
	}

}
