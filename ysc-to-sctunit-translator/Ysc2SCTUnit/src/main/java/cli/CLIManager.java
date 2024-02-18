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
				.desc("path of the Java project, the base path of the execution. Required")
				.build();
		Option sourceDirOpt = Option.builder("sourceDir")
				.argName("arg")
				.hasArg()
				.desc("relative path of the directory where the statechart file is located. Required")
				.build();
		Option sourceFileOpt = Option.builder("sourceFile")
				.argName("arg")
				.hasArg()
				.desc("name of the statechart file without extension, the extension can either be .ysc or .sct. Required")
				.build();
		Option targetDirOpt = Option.builder("targetDir")
				.argName("arg")
				.hasArg()
				.desc("relative path of the directory where the generated packages will be placed, default is \"src\"")
				.build();
		Option targetPackageOpt = Option.builder("targetPackage")
				.argName("arg")
				.hasArg()
				.desc("package where the .java file implementing the statechart will be placed. Required")
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
			
			String projectPath = cmd.getOptionValue("projectPath");
			Files.exists(Paths.get("pathToFileOrDirectory"));
			File f = new File(projectPath);
			String path = f.getAbsolutePath();
			parsedArgs.setProjectName(path.substring(path.lastIndexOf("\\")+1));
			parsedArgs.setWorkspacePath(path.substring(0, path.lastIndexOf("\\")));
			
			String sourceDir = cmd.getOptionValue("sourceDir");
			f = new File(sourceDir);
			path = f.getAbsolutePath();
			parsedArgs.setSourceDir(path);
			
			String sourceFile = cmd.getOptionValue("sourceFile");
			f = new File(sourceFile);
			path = f.getAbsolutePath();
			parsedArgs.setSourceFile(cmd.getOptionValue("sourceFile")+".ysc");
			parsedArgs.setSourceFile(cmd.getOptionValue("sourceFile")+".sct");
			
			String targetPackage = cmd.getOptionValue("targetPackage");
			f = new File(targetPackage);
			path = f.getAbsolutePath();
			parsedArgs.setTargetPackage(path);
			
			
			String targetDir;
			if (cmd.hasOption("targetDir"))
				targetDir = cmd.getOptionValue("targetDir");
			else
				targetDir = cmd.getOptionValue("src");
			f = new File(targetDir);
			path = f.getAbsolutePath();
			parsedArgs.setTargetPackage(path);
			
			if (cmd.hasOption("t")) 
				parsedArgs.setT(true);
			else 
				parsedArgs.setT(false);
				
			return parsedArgs;
		}
	}

}
