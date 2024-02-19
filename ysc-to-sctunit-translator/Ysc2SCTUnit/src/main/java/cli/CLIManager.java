package cli;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
	 * @throws ParseException if there are any problems encountered while parsing the command line tokens.
	 */
	public static ParsedArgs parse(String[] args) throws ParseException {
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
		Option timeService = new Option("t", "timeService", false,
				"enable the generation of a timer service for statecharts that use timed event triggers");
		Option help = new Option("h", "help", false, "print this message");

		options.addOption(projectPathOpt);
		options.addOption(sourceDirOpt);
		options.addOption(sourceFileOpt);
		options.addOption(targetDirOpt);
		options.addOption(targetPackageOpt);
		options.addOption(timeService);
		options.addOption(help);

		// Parse the arguments according to the specified options.
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		
		// For printing the help message
		HelpFormatter formatter = new HelpFormatter();
		
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
				path = new File(projectPath).getAbsolutePath();
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
			
			if (cmd.hasOption("t")) {
				parsedArgs.setT(true);
			}else {
				parsedArgs.setT(false);
			}
			
			return parsedArgs;
		}
	}

}
