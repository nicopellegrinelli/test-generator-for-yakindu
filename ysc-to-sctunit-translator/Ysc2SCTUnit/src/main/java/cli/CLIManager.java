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

public final class CLIManager {

	public static ParsedArgs parse(String[] args) throws ParseException {
		Options options = new Options();
		Option projectPathOpt = Option.builder("projectPath")
				.argName("arg")
				.hasArg()
				.desc("path of the Java project, the base path of the execution; required")
				.build();
		Option sourceDirOpt = Option.builder("sourceDir")
				.argName("arg")
				.hasArg()
				.desc("relative path of the directory where the statechart file is located; required")
				.build();
		Option sourceFileOpt = Option.builder("sourceFile")
				.argName("arg")
				.hasArg()
				.desc("name of the statechart file without extension, the extension can either be .ysc or .sct; required")
				.build();
		Option targetDirOpt = Option.builder("targetDir")
				.argName("arg")
				.hasArg()
				.desc("relative path of the directory where the generated packages will be placed, default is \"src\"")
				.build();
		Option targetPackageOpt = Option.builder("targetPackage")
				.argName("arg")
				.hasArg()
				.desc("package where the .java file implementing the statechart will be placed; required")
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

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		
		return manageCmd(cmd, options);
	}
	
	
	public static ParsedArgs manageCmd(CommandLine cmd, Options options){
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
			
			String projectPath = cmd.getOptionValue("projectPath");
			if(Files.exists(Paths.get(projectPath))) {
				path = new File(projectPath).getPath();
				parsedArgs.setProjectName(path.substring(path.lastIndexOf("\\")+1));
				parsedArgs.setWorkspacePath(path.substring(0, path.lastIndexOf("\\")));
			}else {
				System.out.println("Error with -projectPath option: directory not found at \"" +
									projectPath + "\".");
				return null;
			}
			
			String sourceDir = cmd.getOptionValue("sourceDir");
			if(Files.exists(Paths.get(projectPath + "\\" + sourceDir))) {
				parsedArgs.setSourceDir(new File(sourceDir).getPath());
			}else {
				System.out.println("Error with -sourceDir option: directory not found at \"" +
									projectPath + "\\" + sourceDir + "\".");
				return null;
			}
			
			String sourceFile = cmd.getOptionValue("sourceFile");
			if(Files.exists(Paths.get(projectPath + "\\" + sourceDir + "\\" + sourceFile + ".ysc"))) {
				parsedArgs.setSourceFile(sourceFile + ".ysc");
			}else if (Files.exists(Paths.get(projectPath + "\\" + sourceDir + "\\" + sourceFile  + ".sct"))){
				parsedArgs.setSourceFile(sourceFile + ".sct");
			}else {
				System.out.println("Error with -sourceFile option: file not found at \"" + 
									projectPath + "\\" + sourceDir + "\\" + sourceFile + "\".");
				return null;
			}
			
			String targetDir;
			if (cmd.hasOption("targetDir")) {
				targetDir = cmd.getOptionValue("targetDir");
			}else {
				targetDir = "src";
			}
			parsedArgs.setTargetDir(new File(targetDir).getPath());

			
			String targetPackage = cmd.getOptionValue("targetPackage");
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
