package generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGenerator {

	public static void main(String[] args) throws IOException, InterruptedException {
		//Needed Strings
		String workspacePath = args[0];
		String projectName = args[1];
		String package_name = args[2];
		String statechart_name = args[3];
		String projectPath = workspacePath + "\\" + projectName;
		String scc = "C:\\Program Files (x86)\\itemis_CREATE\\scc.bat";
		String target = "-class " + package_name + "." + statechart_name;
		String options = "-projectCP " + projectPath + "\\bin";
		String base_dir = "-base_dir " + projectPath;
		
		//Itemis sgen file writing
		System.out.println("Starting execution...");
		File genFile = new File(projectPath + "\\model\\" + statechart_name + ".sgen");
		try {
		  FileInputStream fis = new FileInputStream("template\\CodegeneratorTemplate.txt");
		  BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		  BufferedWriter bw = new BufferedWriter(new FileWriter(genFile));
		  String line;
		  while((line=br.readLine())!=null){
			  if (line.contains("basePackage")) {
				  bw.write("\t\t\tbasePackage = \"" +  package_name + "\"" + "\n");
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
		} catch (FileNotFoundException e) {
		  // File not found
		  e.printStackTrace();
		} catch (IOException e) {
		  // Error when reading the file
		  e.printStackTrace();
		}
		genFile.createNewFile();
		
		//Wait to be sure the project is refreshed
		Thread.sleep(1000);

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
		
		//Wait to be sure the project is refreshed
		Thread.sleep(1000);
	
		//Evosuite tests generation
		List<String> evoArgs = new ArrayList<>();
		evoArgs.addAll(Arrays.asList(target.split(" ")));
		evoArgs.addAll(Arrays.asList(options.split(" ")));
		evoArgs.addAll(Arrays.asList(base_dir.split(" ")));
		org.evosuite.EvoSuite.main(evoArgs.toArray(new String[evoArgs.size()]));

	}

}
