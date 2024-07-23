package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ysc2sctunit.ysc.reading.IYscReader;
import ysc2sctunit.ysc.reading.YscReader;

public class StateCounter {

	public static void main(String[] args)
			throws NullPointerException, ParserConfigurationException, SAXException, IOException {
		String csvPath = args[0];
		String baseDirectoryPath = args[1];

		System.out.println("----------------------------");
		System.out.println("StateCounter");
		visit(baseDirectoryPath, csvPath, "");
		System.out.println("----------------------------");
	}

	private static void visit(String baseDirectoryPath, String csvPath, String statechartDirectory)
			throws IOException, NullPointerException, ParserConfigurationException, SAXException {
		for (File file : new File(baseDirectoryPath).listFiles()) {
			if (file.getAbsolutePath().endsWith(".ysc")) {
				String statechartPath = file.getAbsolutePath();
				try {
					IYscReader yscReader = new YscReader(statechartPath);

					String statechartName = yscReader.getStatechartName();

					FileWriter writer;

					System.out.println("----------------------------");
					System.out.println("Retrieving data from " + statechartName + " statechart.");

					if (!Files.exists(Paths.get(csvPath))) {
						System.out.println("-> Initializing  " + csvPath + ".");
						writer = new FileWriter(csvPath, false);
						writer.append("Directory,Statechart,NumStates,AvgDepth,MaxDepth\n");
						writer.close();
					}

					int numStates = 0;
					int maxDepth = 0;
					int sumDepth = 0;

					int depth;
					System.out.println("-> Reading  " + statechartPath + ".");
					for (String stateHierarchy : yscReader.getStatesNames().values()) {
						if (stateHierarchy.contains("_final_"))
							continue;
						depth = getDepth(stateHierarchy);
						maxDepth = Math.max(maxDepth, depth);
						sumDepth += depth;
						numStates++;
					}

					double avgDepth = ((double) sumDepth) / numStates;
					
					String name = statechartPath.substring(
							statechartPath.lastIndexOf('\\')+1,
							statechartPath.lastIndexOf('.'));
					String csv_row = statechartDirectory.substring(1) + "," + name + "," + numStates + "," + avgDepth + ","
							+ maxDepth + "\n";

					System.out.println("-> Writing  " + csvPath + ".");
					writer = new FileWriter(csvPath, true);
					writer.append(csv_row);

					writer.close();
					System.out.println("----------------------------");
				} catch (NullPointerException e) {
					System.err.println("Error with" + statechartPath + " " + e.getMessage());
				}
			}
		}

		for (File dir : new File(baseDirectoryPath).listFiles(File::isDirectory)) {
			System.out.println("\n\nVisiting " + statechartDirectory + "/" + dir.getName());
			visit(dir.getAbsolutePath(), csvPath, statechartDirectory + "/" + dir.getName());
		}
	}

	private static int getDepth(String stateHierarchy) {
		int depth = 0;
		String subStr = stateHierarchy;
		while (subStr.contains(".")) {
			subStr = subStr.substring(subStr.indexOf('.') + 1);
			depth++;
		}
		depth++;
		if (depth % 2 != 0)
			System.err.println("Error with " + stateHierarchy + ", odd number of states");
		return depth / 2;
	}
}
