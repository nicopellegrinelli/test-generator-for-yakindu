package generators;

import java.io.IOException;

import support.CompilerManager;
import support.MySecurityManager;

public class TestGenerator {

	public static void main(String[] args) throws InterruptedException, IOException {
		// Collects all needed Strings 
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
		
		// Generates the .sgen file needed by Itemis Create
		boolean timeBased = args.length == 5 && args[4].equals("time");
		Generators.genarateSgen(projectPath, statechartName, packageName, projectName, timeBased);
		
		// Calls the Itemis Create Java code generator
		Generators.genarateJava(projectPath, itemisScc);
		
		// Compiles the generated classes
		CompilerManager.compile(compilerD, compilerClasspath, classPath);		
		
		// Modifies the generated Java code to create a simplified version
		Generators.genarateSimplifiedJava(classPath, simplifiedClassPath);
		
		// Compiles the new simplified class
		CompilerManager.compile(compilerD, compilerClasspath, simplifiedClassPath);	
		
		// Calls the Evosuite test generator both on the original and the simplified versions
		// Changes the security manager to avoid JVM stop running after Evosuite calls System.exit(0);
		SecurityManager default_sm = System.getSecurityManager();
		MySecurityManager my_sm = new MySecurityManager();
	    System.setSecurityManager(my_sm);
	    try {
	    	Generators.genarateJunit(evoTarget, evoOptions, evoBaseDir);
	    } catch (SecurityException e) {
	    	//Generazione sctunit
	    }

	    try {
	    	Generators.genarateJunit(evoSimplifiedTarget, evoOptions, evoBaseDir);
	    } catch (SecurityException e) { 
	    	//Generazione sctunit
	    }
	    
	    // Changes the security manager back to the default one to let the execution ends
	    System.setSecurityManager(default_sm);
	    System.out.println("Closing...");
	    System.exit(0);
	}

}
