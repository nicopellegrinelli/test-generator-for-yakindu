package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGenerator {

	public static void main(String[] args) throws IOException, InterruptedException {
		//Needed Strings
		String projectPath = args[0];
		String package_name = args[1];
		String class_name = args[2];
		String scc = "C:\\Program Files (x86)\\itemis_CREATE\\scc.bat";
		String target = "-class " + package_name + "." + class_name;
		String options = "-projectCP " + projectPath + "\\bin";
		String base_dir = "-base_dir " + projectPath;
		
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
