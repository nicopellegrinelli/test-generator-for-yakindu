package generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class TestGenerator {

	public static void main(String[] args) throws IOException, InterruptedException {
		//Needed Strings
		String workspacePath = args[0];
		String projectName = args[1];
		String packageName = args[2];
		String statechartName = args[3];
		String projectPath = workspacePath + "\\" + projectName;
		String scc = "C:\\Program Files (x86)\\itemis_CREATE\\scc.bat";
		String compilerD = "-d " + projectPath + "\\bin";
		String compilerClasspath = "-classpath " + projectPath + "\\src\\";
		String evoTarget = "-class " + packageName + "." + statechartName;
		String evoOptions = "-projectCP " + projectPath + "\\bin";
		String evoBase_dir = "-base_dir " + projectPath;
		
		//Itemis sgen file writing
		File genFile = new File(projectPath + "\\model\\" + statechartName + ".sgen");
		try {
		  FileInputStream fis = new FileInputStream("template\\CodegeneratorTemplate.txt");
		  BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		  BufferedWriter bw = new BufferedWriter(new FileWriter(genFile));
		  String line;
		  while((line=br.readLine())!=null){
			  if (line.contains("basePackage")) {
				  bw.write("\t\t\tbasePackage = \"" +  packageName + "\"" + "\n");
				  continue;
			  }
			  if (line.contains("const PROJECT : string")) {
				  bw.write("\tconst PROJECT : string = \"" +  projectName  + "\"" + "\n");
				  continue;
			  }
		      bw.write(line + "\n");
		  }
		  br.close();
		  bw.close();
		} catch (FileNotFoundException e) {// File not found
		  e.printStackTrace();
		} catch (IOException e) {// Error when reading the file
		  e.printStackTrace();
		}
		genFile.createNewFile();
		
		//Itemis code generation
		ProcessBuilder pb = new ProcessBuilder();
		pb.redirectErrorStream(true);
		pb.directory(new File(projectPath));
		pb.command(scc);
		Process p = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
		System.out.println("");
		
		//Compile the new class
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<String> compilationArgs = new ArrayList<String>();
		compilationArgs.addAll(Arrays.asList(compilerD.split(" ")));
		compilationArgs.addAll(Arrays.asList(compilerClasspath.split(" ")));
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
		File f = new File(projectPath + "\\src\\" + packageName + "\\" + statechartName + ".java");
        Iterable<? extends JavaFileObject> compilationUnits = stdFileManager.getJavaFileObjectsFromFiles(Arrays.asList(f));
		compiler.getTask(null, null, null, compilationArgs, null, compilationUnits).call();
		
		//Evosuite tests generation
		List<String> evoArgs = new ArrayList<>();
		evoArgs.addAll(Arrays.asList(evoTarget.split(" ")));
		evoArgs.addAll(Arrays.asList(evoOptions.split(" ")));
		evoArgs.addAll(Arrays.asList(evoBase_dir.split(" ")));
		org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));

	}

}
