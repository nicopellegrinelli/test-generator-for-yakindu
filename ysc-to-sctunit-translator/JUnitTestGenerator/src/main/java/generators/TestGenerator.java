package generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


public class TestGenerator {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Collects all needed Strings 
		String workspacePath = args[0];
		String projectName = args[1];
		String packageName = args[2];
		String statechartName = args[3];
		String projectPath = workspacePath + "\\" + projectName;
		String itemisScc = "C:\\Program Files (x86)\\itemis_CREATE\\scc.bat";
		String compilerD = "-d " + projectPath + "\\bin";
		String compilerClasspath = "-classpath " + projectPath + "\\src\\";
		String compilerImplicit = "-implicit:class";
		String evoTarget = "-class " + packageName + "." + statechartName;
		String evoOptions = "-projectCP " + projectPath + "\\bin";
		String evoBase_dir = "-base_dir " + projectPath;
		
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
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<String> compilationArgs = new ArrayList<String>();
		compilationArgs.addAll(Arrays.asList(compilerD.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerClasspath.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerImplicit));
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
		File f = new File(projectPath + "\\src\\" + packageName + "\\" + statechartName + ".java");
        Iterable<? extends JavaFileObject> compilationUnits = stdFileManager.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
		
		// Calls the Evosuite test generator
		List<String> evoArgs = new ArrayList<>();
		evoArgs.addAll(Arrays.asList(evoTarget.split(" ")));
		evoArgs.addAll(Arrays.asList(evoOptions.split(" ")));
		evoArgs.addAll(Arrays.asList(evoBase_dir.split(" ")));
		org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));
	}

}
