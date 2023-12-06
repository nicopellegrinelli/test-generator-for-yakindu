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

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import support.MySecurityManager;
import visitors.ClassDeclarationVisitor;
import visitors.ConstructorDeclarationVisitor;
import visitors.MethodDeclarationVisitor;


public class TestGenerator {

	public static void main(String[] args) throws IOException, InterruptedException {
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
		String compilerImplicit = "-implicit:class";
		
		String evoTarget = "-class " + packageName + "." + statechartName;
		String evoSimplifiedTarget = "-class " + packageName + "." + statechartName + "Simplified";
		String evoOptions = "-projectCP " + projectPath + "\\bin";
		String evoBaseDir = "-base_dir " + projectPath;
		String evoGenerateSuite = "-generateSuite ";
		
		// Generates the .sgen file needed by Itemis Create
		boolean timeBased = args.length == 5 && args[4].equals("time");
		CodeGeneratorGenerator.genarate(projectPath, statechartName, packageName, projectName, timeBased);
		
		// Calls the Itemis Create Java code generator
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
		reader.close();
		System.out.println("");
		
		// Compiles the generated classes
		System.out.println("Compiling...\n\n");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<String> compilationArgs = new ArrayList<String>();
		compilationArgs.addAll(Arrays.asList(compilerD.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerClasspath.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerImplicit));
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
		File f = new File(classPath);
        Iterable<? extends JavaFileObject> compilationUnits = stdFileManager.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
		
		
		// Modifies the generated Java code to create a simplified version
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
		
		// Compiles the new simplified class
		f = new File(simplifiedClassPath);
        compilationUnits = stdFileManager.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
		
		
		// Calls the Evosuite test generator both on the original and the simplified versions
		// Changes the security manager to avoid JVM stop running after Evosuite calls System.exit(0);
		MySecurityManager sm = new MySecurityManager();
	    System.setSecurityManager(sm);
	    try {
		    List<String> evoArgs = new ArrayList<>();
			evoArgs.addAll(Arrays.asList(evoTarget.split(" ")));
			evoArgs.addAll(Arrays.asList(evoOptions.split(" ")));
			evoArgs.addAll(Arrays.asList(evoBaseDir.split(" ")));
			evoArgs.addAll(Arrays.asList(evoGenerateSuite.split(" ")));
			org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));
	    } catch (SecurityException e) { }

		List<String> evoArgs = new ArrayList<>();
		evoArgs.addAll(Arrays.asList(evoSimplifiedTarget.split(" ")));
		evoArgs.addAll(Arrays.asList(evoOptions.split(" ")));
		evoArgs.addAll(Arrays.asList(evoBaseDir.split(" ")));
		evoArgs.addAll(Arrays.asList(evoGenerateSuite.split(" ")));
		org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));
	}

}
