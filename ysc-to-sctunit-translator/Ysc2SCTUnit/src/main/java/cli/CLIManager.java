package cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * The Class CLIManager.
 */
public final class CLIManager {

	/**
	 * Parses and manages the command line arguments.
	 *
	 * @param args the command line arguments.
	 * @return the parsed command line arguments, null if it is not possible
	 *  to obtain correctly the required options (also in case of -h option)
	 * @throws IOException if any IO errors occur.
	 */
	public static ParsedArgs parse(String[] args) throws IOException {
		// Initializes the options
		Options options = new Options();
		Option projectPathOpt = Option.builder("projectPath")
				.argName("arg")
				.hasArg()
				.desc("path of the Java project, the base path of the execution;"
						+ " required")
				.build();
		Option sourceDirOpt = Option.builder("sourceDir")
				.argName("arg")
				.hasArg()
				.desc("relative path of the directory where the statechart file is located;"
						+ " required")
				.build();
		Option sourceFileOpt = Option.builder("sourceFile")
				.argName("arg")
				.hasArg()
				.desc("name of the statechart file without extension, the extension can either be .ysc or .sct;"
						+ " required")
				.build();
		Option targetDirOpt = Option.builder("targetDir")
				.argName("arg")
				.hasArg()
				.desc("relative path of the directory containing the target packages, "
						+ "if it is an existing source folder, it cannot have subdirectories;"
						+ " default is \"src\"")
				.build();
		Option targetPackageOpt = Option.builder("targetPackage")
				.argName("arg")
				.hasArg()
				.desc("target package where the .java file implementing the statechart will be placed, in dot notation;"
						+ " required")
				.build();
		Option searchBudget = Option.builder("searchBudget")
				.argName("arg")
				.hasArg()
				.desc("the search budget to impose to Evosuite, it must be a positive integer")
				.build();
		Option timeService = new Option("t", "timeService", false,
				"enable the generation of a timer service for statecharts that use timed event triggers");
		Option help = new Option("h", "help", false, "print this message");

		options.addOption(projectPathOpt);
		options.addOption(sourceDirOpt);
		options.addOption(sourceFileOpt);
		options.addOption(targetDirOpt);
		options.addOption(targetPackageOpt);
		options.addOption(searchBudget);
		options.addOption(timeService);
		options.addOption(help);
		
		// For printing the help message
		HelpFormatter formatter = new HelpFormatter();

		// Parse the arguments according to the specified options.
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			formatter.printHelp("ysc2sctunit", options);
			return null;
		}
		
		
		if (cmd.hasOption("h")) {
			formatter.printHelp("ysc2sctunit", options);
			return null;
		} else if (!(cmd.hasOption("projectPath") && cmd.hasOption("sourceDir") &&
					 cmd.hasOption("sourceFile") && cmd.hasOption("targetPackage"))) {
			System.out.println("Missing required options");
			formatter.printHelp("ysc2sctunit", options);
			return null;
		} else {
			ParsedArgs parsedArgs = new ParsedArgs();
			String path;
			
			// Obtain the workspace path and project name from the project path
			String projectPath = cmd.getOptionValue("projectPath");
			if(Files.exists(Paths.get(projectPath))) {
				path = new File(projectPath).getCanonicalPath();
				parsedArgs.setProjectName(path.substring(path.lastIndexOf("\\")+1));
				parsedArgs.setWorkspacePath(path.substring(0, path.lastIndexOf("\\")));
			}else {
				System.out.println("Error with -projectPath option: the directory \"" +
									projectPath + "\" does not exist.");
				return null;
			}
			
			String sourceDir = cmd.getOptionValue("sourceDir");
			if(Files.exists(Paths.get(projectPath + "\\" + sourceDir))) {
				parsedArgs.setSourceDir(new File(sourceDir).getPath());
			}else {
				System.out.println("Error with -sourceDir option: the directory \"" +
									projectPath + "\\" + sourceDir + "\" does not exist.");
				return null;
			}
			
			// Obtain the source file, checking if exists a file with the given name and extension .ysc or .sct
			String sourceFile = cmd.getOptionValue("sourceFile");
			if(Files.exists(Paths.get(projectPath + "\\" + sourceDir + "\\" + sourceFile + ".ysc"))) {
				parsedArgs.setSourceFile(sourceFile + ".ysc");
			}else if (Files.exists(Paths.get(projectPath + "\\" + sourceDir + "\\" + sourceFile  + ".sct"))){
				parsedArgs.setSourceFile(sourceFile + ".sct");
			}else {
				System.out.println("Error with -sourceFile option: the file \"" + 
									projectPath + "\\" + sourceDir + "\\" + sourceFile + 
									"(.ysc|.sct)\" does not exist.");
				return null;
			}
			
			String targetDir;
			if (cmd.hasOption("targetDir")) {
				targetDir = cmd.getOptionValue("targetDir");
			}else {
				targetDir = "src";
			}
			parsedArgs.setTargetDir(new File(targetDir).getPath());

			// Obtain the targetPackage as a subpath (it is given in dot notation)
			String targetPackage = cmd.getOptionValue("targetPackage").replace(".", "\\");
			parsedArgs.setTargetPackage(new File(targetPackage).getPath());
			
			// Try to obtain a usable value for searchBudget
			if (cmd.hasOption("searchBudget")) {
				try {
					int value = Integer.parseInt(cmd.getOptionValue("searchBudget"));
					if (value <= 0) {
						System.out.println("Error with -searchBudget option: the value must be a positive integer");
						return null;
					}
					parsedArgs.setSearchBudget(value);
				}catch (NumberFormatException e) {
					System.out.println("Error with -searchBudget option: the value must be a positive integer");
					return null;
				}
			}
			
			if (cmd.hasOption("t")) {
				parsedArgs.setT(true);
			}else {
				parsedArgs.setT(false);
			}
			
			return parsedArgs;
		}
	}

}
